package com.elyseswoverland.circleconnect.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elyseswoverland.circleconnect.R
import com.elyseswoverland.circleconnect.ui.map.MapFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.content, MapFragment())
        fragmentTransaction.commit()
    }
}
