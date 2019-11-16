package com.elyseswoverland.circleconnect.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.elyseswoverland.circleconnect.R
import com.elyseswoverland.circleconnect.dagger.Dagger
import com.elyseswoverland.circleconnect.models.Profile
import com.elyseswoverland.circleconnect.network.CircleConnectApiManager
import com.elyseswoverland.circleconnect.ui.util.DatePickerDialogFragment
import com.elyseswoverland.circleconnect.ui.util.InputUtil
import kotlinx.android.synthetic.main.fragment_account.*
import rx.android.schedulers.AndroidSchedulers
import java.util.*
import javax.inject.Inject

class AccountFragment : Fragment() {

    @Inject lateinit var circleConnectApiManager: CircleConnectApiManager

    private val cal: Calendar = Calendar.getInstance(Locale.getDefault())
    private val dobYearAdjustment = 18

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
            val dialogFragment = BirthdatePickerDialogFragment()
            dialogFragment.setResultsView(birthdate)
            dialogFragment.show(childFragmentManager.beginTransaction(), DatePickerDialogFragment.TAG)
        }

        saveButton.setOnClickListener {
            val profile = Profile(firstName.text.toString(), lastName.text.toString(), email.text.toString(),
                    zipCode.text.toString(), birthdate.text.toString())
            circleConnectApiManager.setUserProfile(profile)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onSetProfileSuccess, this::onSetProfileFailure)
        }

        circleConnectApiManager.userProfile
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetProfileSuccess, this::onGetProfileFailure)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        menu?.findItem(R.id.preferences)?.isVisible = false
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

    private fun onSetProfileSuccess(success: Boolean) {
        Toast.makeText(context, "Profile saved successfully!", Toast.LENGTH_LONG).show()
    }

    private fun onSetProfileFailure(throwable: Throwable) {
        throwable.printStackTrace()
    }
}