package com.elyseswoverland.circleconnect.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.*


class LoginFragment : Fragment() {
    private lateinit var callbackManager: CallbackManager


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.elyseswoverland.circleconnect.R.layout.fragment_login, container, false)
        callbackManager = CallbackManager.Factory.create()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setReadPermissions(Arrays.asList(EMAIL, PUBLIC_PROFILE))
        loginButton.fragment = this

        loginButton.registerCallback(callbackManager, object: FacebookCallback<LoginResult?> {
            override fun onSuccess(result: LoginResult?) {
                setFacebookData(result!!)
            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException?) {
                error!!.printStackTrace()
            }
        })
    }

    private fun setFacebookData(loginResult: LoginResult) {
        val graphRequest = GraphRequest.newMeRequest(loginResult.accessToken) { `object`, response ->
            val email = response.jsonObject.getString("email")
            val name = response.jsonObject.getString("name")
            val firstName = response.jsonObject.getString("first_name")
            val lastName = response.jsonObject.getString("last_name")

            Log.d("TAG", "name: $name")
            Log.d("TAG", "email: $email")
        }

        val parameters = Bundle()
        parameters.putString("fields", "id, name, first_name, last_name, email, age_range, gender, locale, timezone, updated_time, verified")
        graphRequest.parameters = parameters
        graphRequest.executeAsync()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val EMAIL = "email"
        private const val PUBLIC_PROFILE = "public_profile"
    }
}