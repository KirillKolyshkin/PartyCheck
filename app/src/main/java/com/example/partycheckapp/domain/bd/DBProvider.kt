package com.example.partycheckapp.domain.bd

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.constraintlayout.widget.Constraints.TAG
import com.example.partycheckapp.data.party.Party
import com.example.partycheckapp.data.user.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.reactivex.Maybe
import io.reactivex.rxkotlin.subscribeBy
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class DBProvider(
    private val database: FirebaseFirestore,
    private val sharedPreferences: SharedPreferences,
    private val firebaseStorage: FirebaseStorage
) {

    fun addImageToStorage(name: String, bitmap: Bitmap, path: String): Maybe<StorageReference> {
        var storageRef = firebaseStorage.reference
        var imagesRef: StorageReference? = storageRef
            .child("images")
            .child(path)//"userImages"
            .child("$name.jpg")

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos)
        val data = baos.toByteArray()
        return Maybe.create { emmiter ->
            var uploadTask = imagesRef?.putBytes(data)?.addOnSuccessListener { emmiter.onSuccess(imagesRef) }
        }
    }

    fun tryToAddImageToStorage(bitmap: Bitmap?, name: String): Maybe<String> {
        return Maybe.create { emmiter ->
            if (bitmap != null) {
                val disposable = addImageToStorage(name, bitmap, "partyImages").subscribeBy(onSuccess = {
                    val ref = it
                    val t = ref?.downloadUrl?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downloadUri = task.result
                            emmiter.onSuccess(downloadUri.toString())
                        } else {
                            emmiter.onError(Exception())
                        }
                    }
                })
            } else {
                emmiter.onError(Exception())
            }
        }
    }

    fun addNewParty(
        title: String,
        description: String?,
        location: String,
        date: Date,
        password: String,
        bitmap: Bitmap?
    ) {
        val userId = sharedPreferences.getString(CURRENT_USER, "")
        val newUserRef = database.collection("users").document(userId).collection("parties").document()
        val newPartyRef = database.collection("parties").document()
        val disposable = tryToAddImageToStorage(bitmap, title + description).subscribeBy(
            onSuccess = {
                val downloadUrl = it
                val disposableUser = getCurrentUser().subscribeBy(onSuccess = { user ->
                    val users = ArrayList<User>()
                    users.add(user)
                    val party = Party(
                        title,
                        description,
                        location,
                        date,
                        password,
                        user,
                        users,
                        ArrayList(),
                        downloadUrl
                    )
                    newPartyRef.set(party).onSuccessTask {
                        newUserRef.set(party)
                    }
                })

            },
            onError = {
                val disposableUser = getCurrentUser().subscribeBy(onSuccess = { user ->
                    val users = ArrayList<User>()
                    users.add(user)
                    val party = Party(
                        title,
                        description,
                        location,
                        date,
                        password,
                        user,
                        users,
                        ArrayList(),
                        null
                    )
                    newPartyRef.set(party).onSuccessTask {
                        newUserRef.set(party)
                    }
                })
            }
        )
    }

    fun updateCurrentUser(name: String, phone: String, cardNumber: Long?, bitmap: Bitmap?) {

        val userId = sharedPreferences.getString(CURRENT_USER, "")
        val docRef = database.collection("users").document(userId)
        if (bitmap != null) {
            val disposable = addImageToStorage(name, bitmap, "userImages").subscribeBy(onSuccess = {
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

    fun getUserParties(): Maybe<ArrayList<Party>> {
        val userId = sharedPreferences.getString(CURRENT_USER, "")
        return Maybe.create { emmiter ->
            val docRef = database.collection("users")
                .document(userId)
                .collection("parties")
                .get()
                .addOnSuccessListener { result ->
                    val partyList: ArrayList<Party> = ArrayList()
                    for (document in result) {
                        partyList.add(document as Party)
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }

                    emmiter.onSuccess(partyList)
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                    emmiter.onError(exception)
                }
        }
    }

    fun addNewUser(name: String, phone: String, cardNumber: Long?, imgUrl: String?) {
        val user = User(name, phone, cardNumber, imgUrl, ArrayList())
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

    fun getCurrentUser(): Maybe<User> =
        Maybe.create { emmiter ->
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

    companion object {
        const val CURRENT_USER = "current_user_id"
    }

}
