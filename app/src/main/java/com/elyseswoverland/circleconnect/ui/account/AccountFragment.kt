package com.elyseswoverland.circleconnect.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elyseswoverland.circleconnect.R
import com.elyseswoverland.circleconnect.dagger.Dagger
import com.elyseswoverland.circleconnect.models.Profile
import com.elyseswoverland.circleconnect.network.CircleConnectApiManager
import com.elyseswoverland.circleconnect.ui.util.DatePickerDialogFragment
import com.elyseswoverland.circleconnect.ui.util.InputUtil
import kotlinx.android.synthetic.main.fragment_account.*
import rx.android.schedulers.AndroidSchedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AccountFragment : Fragment() {

    @Inject lateinit var circleConnectApiManager: CircleConnectApiManager

    private val cal: Calendar = Calendar.getInstance(Locale.getDefault())
    private val dobYearAdjustment = 18

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dagger.getInstance().component().inject(this)
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - dobYearAdjustment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            (childFragmentManager.findFragmentByTag(DatePickerDialogFragment.TAG) as DatePickerDialogFragment)
                    .setResultsView(birthdate)
        }

        birthdate.setOnClickListener {
            InputUtil.hideKeyboard(it)
            val dialogFragment = DatePickerDialogFragment()
            dialogFragment.maxDate = Calendar.getInstance()
            dialogFragment.calendar = cal
            dialogFragment.setDateValidator(null)
            dialogFragment.setDateFormatter(SimpleDateFormat("M/d", Locale.getDefault()))
            dialogFragment.setResultsView(birthdate)
            dialogFragment.show(childFragmentManager.beginTransaction(), DatePickerDialogFragment.TAG)
        }

        circleConnectApiManager.userProfile
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetProfileSuccess, this::onGetProfileFailure)
    }

    private fun onGetProfileSuccess(profile: Profile) {
        firstName.setText(profile.firstName)
        lastName.setText(profile.lastName)
        email.setText(profile.email)
        zipCode.setText(profile.zipCode)
        birthdate.setText(profile.birthday)
    }

    private fun onGetProfileFailure(throwable: Throwable) {
        throwable.printStackTrace()
    }
}