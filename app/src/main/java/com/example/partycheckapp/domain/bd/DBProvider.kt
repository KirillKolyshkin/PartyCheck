package com.example.partycheckapp.domain.bd

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.constraintlayout.widget.Constraints.TAG
import com.example.partycheckapp.data.party.Party
import com.example.partycheckapp.data.party.Purchase
import com.example.partycheckapp.data.user.User
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import io.reactivex.Maybe
import io.reactivex.rxkotlin.subscribeBy
import java.io.ByteArrayOutputStream
import java.util.*

class DBProvider(
    private val database: FirebaseFirestore,
    private val sharedPreferences: SharedPreferences,
    private val firebaseStorage: FirebaseStorage
) {

    fun addImageToStorage(name: String, bitmap: Bitmap): Maybe<StorageReference> {
        var storageRef = firebaseStorage.reference
        var imagesRef: StorageReference? = storageRef
            .child("images")
            .child("userImages")
            .child("$name.jpg")

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos)
        val data = baos.toByteArray()
        return Maybe.create { emmiter ->
            var uploadTask = imagesRef?.putBytes(data)?.addOnSuccessListener { emmiter.onSuccess(imagesRef) }
        }
    }

    fun updateCurrentUser(name: String, phone: String, cardNumber: Long?, bitmap: Bitmap?) {

        val userId = sharedPreferences.getString(CURRENT_USER, "")
        val docRef = database.collection("users").document(userId)
        if (bitmap != null) {
            val disposable = addImageToStorage(name, bitmap).subscribeBy(onSuccess = {
                val ref = it
                val t = ref?.downloadUrl?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        docRef.update(
                            mapOf(
                                "name" to name,
                                "phoneNumber" to phone,
                                "cardNumber" to cardNumber,
                                "imageUrl" to downloadUri.toString()
                            )
                        )
                    }
                }
            })

        } else {
            docRef.update(
                mapOf(
                    "name" to name,
                    "phoneNumber" to phone,
                    "cardNumber" to cardNumber
                )
            )
        }
    }


    fun addNewParty(
        title: String,
        description: String?,
        location: String,
        date: Date,
        password: String,
        organization: String,
        participantList: ArrayList<User>,
        purchaseList: ArrayList<Purchase>,
        imageUrl: String?
    ) {
        val party =
            Party(title, description, location, date, password, organization, participantList, purchaseList, imageUrl)
        
        val newPartyRef = database.collection("parties").document()

    }

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