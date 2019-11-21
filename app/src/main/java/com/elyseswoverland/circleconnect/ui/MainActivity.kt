package com.elyseswoverland.circleconnect.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.elyseswoverland.circleconnect.R
import com.elyseswoverland.circleconnect.dagger.Dagger
import com.elyseswoverland.circleconnect.persistence.AppPreferences
import com.elyseswoverland.circleconnect.persistence.SessionStorage
import com.elyseswoverland.circleconnect.ui.account.AccountFragment
import com.elyseswoverland.circleconnect.ui.favorites.FavoritesFragment
import com.elyseswoverland.circleconnect.ui.login.LoginCallback
import com.elyseswoverland.circleconnect.ui.login.LoginFragment
import com.elyseswoverland.circleconnect.ui.map.MapFragment
import com.elyseswoverland.circleconnect.ui.messages.MessagesFragment
import com.elyseswoverland.circleconnect.ui.preferences.PreferencesActivity
import com.facebook.login.LoginManager
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject




class MainActivity : AppCompatActivity(), LoginCallback {

    @Inject lateinit var sessionStorage: SessionStorage

    @Inject lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dagger.getInstance().component().inject(this)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.content, MapFragment())
        fragmentTransaction.commit()

        supportActionBar?.show()
        bottom_navigation.selectedItemId = R.id.action_map

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
                    if (appPreferences.hasToken()) {
                        fragmentTransaction.replace(R.id.content, FavoritesFragment())
                    } else {
                        fragmentTransaction.replace(R.id.content, LoginFragment.newInstance("favorites"))
                    }
                    fragmentTransaction.commit()
                    true
                }
                R.id.action_messages -> {
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    if (appPreferences.hasToken()) {
                        fragmentTransaction.replace(R.id.content, MessagesFragment())
                    } else {
                        fragmentTransaction.replace(R.id.content, LoginFragment.newInstance("messages"))
                    }
                    fragmentTransaction.commit()
                    true
                }
                R.id.action_account -> {
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    if (appPreferences.hasToken()) {
                        fragmentTransaction.replace(R.id.content, AccountFragment())
                    } else {
                        fragmentTransaction.replace(R.id.content, LoginFragment.newInstance("account"))
                    }
                    fragmentTransaction.commit()
                    true
                }
                else -> true
            }
        }
    }

    override fun loadFullExperience(callingFragment: String) {
        supportActionBar?.show()
        when (callingFragment) {
            "favorites" -> {
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.content, FavoritesFragment())
                fragmentTransaction.commit()
            }
            "messages" -> {
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.content, MessagesFragment())
                fragmentTransaction.commit()
            }
            "account" -> {
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.content, AccountFragment())
                fragmentTransaction.commit()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.preferences -> {
                startActivity(Intent(this@MainActivity, PreferencesActivity::class.java))
                true
            }
            R.id.logout -> {
                appPreferences.token = null
                LoginManager.getInstance().logOut()
                startActivity(Intent(this, MainActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
