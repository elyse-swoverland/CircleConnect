package com.elyseswoverland.circleconnect.ui.preferences

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elyseswoverland.circleconnect.R
import com.elyseswoverland.circleconnect.dagger.Dagger
import com.elyseswoverland.circleconnect.models.CustomerSetting
import com.elyseswoverland.circleconnect.network.CircleConnectApiManager
import com.elyseswoverland.circleconnect.persistence.AppPreferences
import kotlinx.android.synthetic.main.activity_preferences.*
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class PreferencesActivity : AppCompatActivity() {
    private var sortPositionPicked = 0;
    private var distancePicked = 10

    @Inject lateinit var circleConnectApiManager: CircleConnectApiManager

    @Inject lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dagger.getInstance().component().inject(this)
        setContentView(com.elyseswoverland.circleconnect.R.layout.activity_preferences)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        // TODO: - Confirm this
        val paths = arrayOf("by Category", "by Distance", "by Newest Members")
        val adapter = ArrayAdapter<String>(this@PreferencesActivity,
                R.layout.item_spinner, paths)

        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
        spinner.adapter = adapter
        spinner.setSelection(appPreferences.sortByPreference, false)

        getCustomerPrefs()

        saveButton.setOnClickListener {
            appPreferences.sortByPreference = sortPositionPicked
            val customerPrefs = CustomerSetting(appPreferences.custId, spinner.selectedItemPosition + 1,
                    artsEntertainmentCheckbox.isChecked, restaurantsFoodCheckbox.isChecked, retailCheckbox.isChecked,
                    serviceCheckbox.isChecked, distancePicked)
            circleConnectApiManager.updateCustomerPrefs(customerPrefs)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onSetCustomerPrefsSuccess, this::onSetCustomerPrefsFailure)
        }

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sortPositionPicked = position
            }

        }

        distancePicker.setListener {
            distancePicked = it
        }
    }

    private fun getCustomerPrefs() {
        circleConnectApiManager.getCustomerPrefs()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetCustomerPrefsSuccess, this::onGetCustomerPrefsFailure)
    }

    private fun onGetCustomerPrefsSuccess(customerSetting: CustomerSetting) {
        restaurantsFoodCheckbox.isChecked = customerSetting.filterFood
        retailCheckbox.isChecked = customerSetting.filterRetail
        artsEntertainmentCheckbox.isChecked = customerSetting.filterArts
        serviceCheckbox.isChecked = customerSetting.filterService

        distancePicker.value = customerSetting.distanceValue

        rootLayout.visibility = View.VISIBLE
    }

    private fun onGetCustomerPrefsFailure(throwable: Throwable) {

    }

    private fun onSetCustomerPrefsSuccess(success: Boolean) {
        Log.d("TAG", "Prefs saved")
        Toast.makeText(this, "Prefs saved!", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun onSetCustomerPrefsFailure(throwable: Throwable) {
        throwable.printStackTrace()
    }
}