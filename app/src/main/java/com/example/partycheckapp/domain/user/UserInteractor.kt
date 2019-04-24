package com.example.partycheckapp.domain.user

import android.content.SharedPreferences
import android.util.Log
import androidx.constraintlayout.widget.Constraints.TAG
import com.example.partycheckapp.data.debtors.UserDebtor
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import io.reactivex.Maybe
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class UserInteractor(private val auth: FirebaseAuth, private val sharedPreferences: SharedPreferences) {

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

    fun sentCode(phone: String): Maybe<String> {

        return Maybe.create { emitter ->
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        emitter.onComplete()
                        Log.d(TAG, "onVerificationCompleted:$credential")
                        signInWithPhoneAuthCredential(credential)
                        //signInPresenter.authPassed()
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        emitter.onError(e)
                    }

                    override fun onCodeSent(
                        verificationId: String?,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        Log.d(TAG, "onCodeSent:$verificationId")
                        // Save verification ID and resending token so we can use them ))later

                        sharedPreferences.edit().putString(CURRENT_VERIFICATION_ID, verificationId).apply()
                        //resendToken = token
                        emitter.onSuccess(verificationId?:"")
                        //signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(verificationId, "433155"), context)
                        // ...

                    }
                })
        }
    }

    fun verifyPhoneNumber(code: String) {
        var verificationId = sharedPreferences.getString(CURRENT_VERIFICATION_ID, "")
        signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(verificationId, code))
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential): Boolean {
        var response = false
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success")
                response = true
                val user = task.result?.user
                user?.let { auth.updateCurrentUser(it) }
                sharedPreferences.edit()
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
        return auth.currentUser
    }

    companion object {
        const val CURRENT_CODE = "current_code"
        const val CURRENT_PHONE = "current_phone"
        const val CURRENT_VERIFICATION_ID = "current_verification_id"
    }
}