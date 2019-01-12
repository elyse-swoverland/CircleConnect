package com.elyseswoverland.circleconnect.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elyseswoverland.circleconnect.R
import com.elyseswoverland.circleconnect.ui.account.AccountFragment
import com.elyseswoverland.circleconnect.ui.favorites.FavoritesFragment
import com.elyseswoverland.circleconnect.ui.map.MapFragment
import com.elyseswoverland.circleconnect.ui.messages.MessagesFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.content, MapFragment())
        fragmentTransaction.commit()

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_map -> {
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.content, MapFragment())
                    fragmentTransaction.commit()
                    true
                }
                R.id.action_favorites -> {
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.content, FavoritesFragment())
                    fragmentTransaction.commit()
                    true
                }
                R.id.action_messages -> {
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.content, MessagesFragment())
                    fragmentTransaction.commit()
                    true
                }
                R.id.action_account -> {
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.content, AccountFragment())
                    fragmentTransaction.commit()
                    true
                }
                else -> true
            }
        }
    }
}
