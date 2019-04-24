package com.example.partycheckapp.domain.bd

import android.content.SharedPreferences
import android.util.Log
import androidx.constraintlayout.widget.Constraints.TAG
import com.example.partycheckapp.data.user.User
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Maybe

class DBProvider(private val database: FirebaseFirestore, private val sharedPreferences: SharedPreferences) {

    fun addNewUser(name: String, phone: String, cardNumber: Long?, imgUrl: String?) {
        val user = User(name, phone, cardNumber, imgUrl)
        val newUserRef = database.collection("users").document()
        newUserRef.set(user)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot written with ID: $newUserRef")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
        sharedPreferences
            .edit()
            .putString(CURRENT_USER, newUserRef.id)
            .apply()

    }

    fun getCurrentUser(): Maybe<User> {
        return Maybe.create { emmiter ->
            val userId = sharedPreferences.getString(CURRENT_USER, "")
            val docRef = database.collection("users").document(userId)
            docRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    val user = documentSnapshot.toObject(User::class.java)
                    user?.let { emmiter.onSuccess(it) }
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }
    }

    companion object {
        const val CURRENT_USER = "current_user_id"
    }

}