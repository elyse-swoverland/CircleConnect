package com.elyseswoverland.circleconnect.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elyseswoverland.circleconnect.R
import com.elyseswoverland.circleconnect.dagger.Dagger
import com.elyseswoverland.circleconnect.persistence.AppPreferences
import com.elyseswoverland.circleconnect.persistence.SessionStorage
import com.elyseswoverland.circleconnect.ui.account.AccountFragment
import com.elyseswoverland.circleconnect.ui.favorites.FavoritesFragment
import com.elyseswoverland.circleconnect.ui.login.LoginFragment
import com.elyseswoverland.circleconnect.ui.map.MapFragment
import com.elyseswoverland.circleconnect.ui.messages.MessagesFragment
import com.elyseswoverland.circleconnect.ui.preferences.PreferencesActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject




class MainActivity : AppCompatActivity() {

    @Inject lateinit var sessionStorage: SessionStorage

    @Inject lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dagger.getInstance().component().inject(this)
        setContentView(com.elyseswoverland.circleconnect.R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(com.elyseswoverland.circleconnect.R.id.content, MapFragment())
        fragmentTransaction.commit()

        Log.d("INTERCEPTOR", "Token: ${appPreferences.token}")

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                com.elyseswoverland.circleconnect.R.id.action_map -> {
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(com.elyseswoverland.circleconnect.R.id.content, MapFragment())
                    fragmentTransaction.commit()
                    true
                }
                com.elyseswoverland.circleconnect.R.id.action_favorites -> {
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    if (appPreferences.hasToken()) {
                        fragmentTransaction.replace(com.elyseswoverland.circleconnect.R.id.content, FavoritesFragment())
                    } else {
                        fragmentTransaction.replace(com.elyseswoverland.circleconnect.R.id.content, LoginFragment())
                    }
                    fragmentTransaction.commit()
                    true
                }
                com.elyseswoverland.circleconnect.R.id.action_messages -> {
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    if (appPreferences.hasToken()) {
                        fragmentTransaction.replace(com.elyseswoverland.circleconnect.R.id.content, MessagesFragment())
                    } else {
                        fragmentTransaction.replace(com.elyseswoverland.circleconnect.R.id.content, LoginFragment())
                    }
                    fragmentTransaction.commit()
                    true
                }
                com.elyseswoverland.circleconnect.R.id.action_account -> {
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    if (appPreferences.hasToken()) {
                        fragmentTransaction.replace(com.elyseswoverland.circleconnect.R.id.content, AccountFragment())
                    } else {
                        fragmentTransaction.replace(com.elyseswoverland.circleconnect.R.id.content, LoginFragment())
                    }
                    fragmentTransaction.commit()
                    true
                }
                else -> true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(com.elyseswoverland.circleconnect.R.menu.toolbar_menu, menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.preferences -> {
                startActivity(Intent(this@MainActivity, PreferencesActivity::class.java))
                true
            }
            R.id.logout -> {
                Toast.makeText(this@MainActivity, "Logout", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
