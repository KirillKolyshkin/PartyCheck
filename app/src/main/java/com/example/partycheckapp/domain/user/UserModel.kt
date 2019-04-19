package com.example.partycheckapp.domain.user

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.constraintlayout.widget.Constraints.TAG
import com.example.partycheckapp.data.debtors.UserDebtor
import com.example.partycheckapp.presentation.feature.auth.fragment.sign_in.presenter.SignInPresenter
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class UserModel {


    var auth: FirebaseAuth? = null

    fun getDebtor(): UserDebtor {

        return UserDebtor(
            1, "UserName", 88005553535, 5555, Random.nextDouble(-100.0, 100.0)
        )
    }

    fun getTestDebtors(): ArrayList<UserDebtor> {
        val list = java.util.ArrayList<UserDebtor>()
        for (item: Int in 1..10) {
            list.add(getDebtor())
        }
        return list
    }

    fun sentCode(phone: String, context: Activity) {
        auth = FirebaseAuth.getInstance()

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:$credential")
                signInWithPhoneAuthCredential(credential, context)
                var presenter = SignInPresenter(UserModel())
                presenter.authPassed()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.w(TAG, "onVerificationFailed", e)
                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }
            }

            override fun onCodeSent(
                verificationId: String?,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId!!)
                // Save verification ID and resending token so we can use them later

                val t: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)
                t.edit().putString(CURRENT_VERIFICAtiON_ID, verificationId).apply()
                //resendToken = token

                //signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(verificationId, "433155"), context)
                // ...

            }
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone, 60, TimeUnit.SECONDS, context, callbacks)
    }

    fun tryToEnterWithoutAuth(activity: Activity): Boolean {
        val t: SharedPreferences = activity.getSharedPreferences(PREFS_FILENAME, 0)
        var verificationId = t.getString(CURRENT_VERIFICAtiON_ID, null)
        var code = t.getString(CURRENT_CODE, null)
        return if (verificationId != null && code != null) {
            signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(verificationId, code), activity)
        } else {
            false
        }
    }

    fun verifyPhoneNumber(code: String, activity: Activity) {
        val t: SharedPreferences = activity.getSharedPreferences(PREFS_FILENAME, 0)
        var verificationId = t.getString(CURRENT_VERIFICAtiON_ID, "")
        signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(verificationId, code), activity)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, activity: Activity): Boolean {
        var response = false
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    response = true
                    val user = task.result?.user
                    user?.let { auth?.updateCurrentUser(it) }
                    val t: SharedPreferences = activity.getSharedPreferences(PREFS_FILENAME, 0)
                    t.edit()
                        .putString(CURRENT_CODE, credential.smsCode)
                        .putString(CURRENT_PHONE, user?.phoneNumber)
                        .apply()
                    // ...
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                }
            }
        return response
    }

    fun getUser(): FirebaseUser? {
        return auth?.currentUser
    }

    companion object {
        val PREFS_FILENAME = "com.user.prefs"
        val CURRENT_CODE = "current_code"
        val CURRENT_PHONE = "current_phone"
        val CURRENT_VERIFICAtiON_ID = "current_verification_id"
    }
}