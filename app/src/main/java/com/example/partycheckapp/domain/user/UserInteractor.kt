package com.example.partycheckapp.domain.user

import android.content.SharedPreferences
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import io.reactivex.Maybe
import java.util.concurrent.TimeUnit

class UserInteractor(
    private val auth: FirebaseAuth,
    private val sharedPreferences: SharedPreferences
) {

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
                        signInWithPhoneAuthCredential(credential)
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        emitter.onError(e)
                    }

                    override fun onCodeSent(
                        verificationId: String?,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        sharedPreferences.edit().putString(CURRENT_VERIFICATION_ID, verificationId).apply()
                        emitter.onSuccess(verificationId ?: "")
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
                response = true
                val user = task.result?.user
                user?.let { auth.updateCurrentUser(it) }
                sharedPreferences.edit()
                    .putString(CURRENT_CODE, credential.smsCode)
                    .putString(CURRENT_PHONE, user?.phoneNumber)
                    .apply()
            } else {
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
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
