package com.elyseswoverland.circleconnect.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elyseswoverland.circleconnect.dagger.Dagger
import com.elyseswoverland.circleconnect.models.Session
import com.elyseswoverland.circleconnect.models.SessionRequest
import com.elyseswoverland.circleconnect.network.CircleConnectApiManager
import com.elyseswoverland.circleconnect.persistence.AppPreferences
import com.elyseswoverland.circleconnect.persistence.SessionStorage
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
import rx.android.schedulers.AndroidSchedulers
import java.util.*
import javax.inject.Inject

class LoginFragment : Fragment() {
    private lateinit var callbackManager: CallbackManager
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var ctx: Context

    @Inject
    lateinit var circleConnectApiManager: CircleConnectApiManager

    @Inject lateinit var sessionStorage: SessionStorage

    @Inject lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dagger.getInstance().component().inject(this)
    }

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

    // Facebook Sign-in
    private fun setFacebookData(loginResult: LoginResult) {
        val graphRequest = GraphRequest.newMeRequest(loginResult.accessToken) { `object`, response ->
            val email = response.jsonObject.getString("email")
            val firstName = response.jsonObject.getString("first_name")
            val lastName = response.jsonObject.getString("last_name")
            val id = response.jsonObject.getString("id")

            val sessionRequest = SessionRequest(id, "facebook", firstName, lastName, email, null, "")
            circleConnectApiManager.startSession(sessionRequest)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onAppLoginSuccess, this::onAppLoginFailure)
        }

        val parameters = Bundle()
        parameters.putString("fields", "id, name, first_name, last_name, email, age_range, gender, locale, timezone, updated_time, verified")
        graphRequest.parameters = parameters
        graphRequest.executeAsync()
    }

    // Google Sign-in
    private fun handleSignInResult(completedTask: com.google.android.gms.tasks.Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            account?.let {
                val sessionRequest = SessionRequest(account.id!!, "google", account.givenName!!,
                        account.familyName!!, account.email!!, null, "")
            circleConnectApiManager.startSession(sessionRequest)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onAppLoginSuccess, this::onAppLoginFailure)
            }
        } catch (e: ApiException) {
            Log.w("TAG", "signInResult:failed code=" + e.statusCode)
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

    private fun onAppLoginSuccess(session: Session) {
        Log.d("TAG", "Token: ${session.token}")
        sessionStorage.session = session

        appPreferences.token = session.token
        appPreferences.custId = session.customerId
    }

    private fun onAppLoginFailure(throwable: Throwable) {
        throwable.printStackTrace()
    }

    companion object {
        private const val EMAIL = "email"
        private const val PUBLIC_PROFILE = "public_profile"
    }
}