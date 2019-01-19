package com.elyseswoverland.circleconnect.ui.login

import android.content.Context
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.*






class LoginFragment : Fragment() {
    private lateinit var callbackManager: CallbackManager
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var ctx: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.elyseswoverland.circleconnect.R.layout.fragment_login, container, false)
        callbackManager = CallbackManager.Factory.create()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ctx = context ?: return

        // Facebook
        facebookLoginButton.setReadPermissions(Arrays.asList(EMAIL, PUBLIC_PROFILE))
        facebookLoginButton.fragment = this
        facebookLoginButton.registerCallback(callbackManager, object: FacebookCallback<LoginResult?> {
            override fun onSuccess(result: LoginResult?) {
                setFacebookData(result!!)
            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException?) {
                error!!.printStackTrace()
            }
        })

        // Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(ctx, gso)
        googleLoginButton.setSize(SignInButton.SIZE_STANDARD)
        googleLoginButton.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, 69)
        }
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

    private fun handleSignInResult(completedTask: com.google.android.gms.tasks.Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
//            updateUI(account)
            Log.d("TAG", "Google Login Success: " + account!!.displayName)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.statusCode)
//            updateUI(null)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 69) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val EMAIL = "email"
        private const val PUBLIC_PROFILE = "public_profile"
    }
}