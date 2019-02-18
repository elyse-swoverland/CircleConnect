package com.elyseswoverland.circleconnect.ui.preferences

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.elyseswoverland.circleconnect.R
import kotlinx.android.synthetic.main.activity_preferences.*


class PreferencesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.elyseswoverland.circleconnect.R.layout.activity_preferences)

        val paths = arrayOf("Category", "Distance", "Newest Members")
        val adapter = ArrayAdapter<String>(this@PreferencesActivity,
                R.layout.item_spinner, paths)

        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
        spinner.adapter = adapter
    }
}