package com.example.partycheckapp.domain.bd

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.util.Log
import androidx.constraintlayout.widget.Constraints.TAG
import com.example.partycheckapp.data.debtors.Debt
import com.example.partycheckapp.data.party.Party
import com.example.partycheckapp.data.party.Purchase
import com.example.partycheckapp.data.user.User
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.reactivex.Maybe
import io.reactivex.rxkotlin.subscribeBy
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

class DBProvider(
    private val database: FirebaseFirestore,
    private val sharedPreferences: SharedPreferences,
    private val firebaseStorage: FirebaseStorage
) {

    fun addImageToStorage(name: String, bitmap: Bitmap, path: String): Maybe<String> {
        var storageRef = firebaseStorage.reference
        var imagesRef: StorageReference? = storageRef
            .child("images")
            .child(path)//"userImages"
            .child("$name.jpg")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos)
        val data = baos.toByteArray()
        return Maybe.create { emitter ->
            var uploadTask = imagesRef?.putBytes(data)?.addOnSuccessListener {
                imagesRef.downloadUrl.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        emitter
                            .onSuccess(downloadUri.toString())
                    }
                }
            }
        }
    }

    fun getUserDebtors(): Maybe<ArrayList<Debt>> =
        Maybe.create { emitter ->
            val disposavle = getCurrentUser().subscribeBy(
                onSuccess = { currentUser ->
                    database.collection("debts").get().addOnSuccessListener { result ->
                        val debtList: ArrayList<Debt> = ArrayList()
                        for (document in result) {
                            if (document.toObject(Debt::class.java).creditor.name == currentUser.name ||
                                document.toObject(Debt::class.java).debtor.name == currentUser.name
                            ) {
                                debtList.add(document.toObject(Debt::class.java))
                            }
                            Log.d(TAG, "${document.id} => ${document.data}")
                        }
                        emitter.onSuccess(debtList)
                    }
                })
        }

    fun getDebt(debtRef: String): Maybe<Debt> =
        Maybe.create { emitter ->
            database.collection("debts")
                .document(debtRef).get().addOnSuccessListener { documentSnapshot ->
                    val debt = documentSnapshot.toObject(Debt::class.java)
                    debt?.let { emitter.onSuccess(it) }
                }
        }

    fun removeRepaidDebt(debtRef: String) {
        val disposable = getDebt(debtRef).subscribeBy(
            onSuccess = {
                if (it.debtorApproval && it.creditorApproval)
                    database.collection("debts")
                        .document(debtRef)
                        .delete()
            })
    }

    fun updateDebt(debtRef: String, boolean: Boolean) {
        val disposable = getDebt(debtRef).subscribeBy(
            onSuccess = {
                if (boolean) {
                    val creditorApproval = it.creditorApproval
                    database.collection("debts")
                        .document(debtRef)
                        .update("creditorApproval", !creditorApproval)
                } else {
                    val debtorApproval = it.debtorApproval
                    database.collection("debts")
                        .document(debtRef)
                        .update("debtorApproval", !debtorApproval)
                }
                removeRepaidDebt(debtRef)
            }
        )
    }

    private fun updateUserInAllDebts(name: String, phone: String, cardNumber: String?, uri: String?) {
        val disposable = getUserDebtors().subscribeBy(
            onSuccess = {
                val newDebtList = ArrayList<Debt>()
                for (debt in it) {
                    if (debt.creditor.name == name) {
                        database.collection("debts")
                            .document(debt.ref)
                            .update("creditor", User(name, phone, cardNumber, uri))
                    }
                    if (debt.debtor.name == name) {
                        database.collection("debts")
                            .document(debt.ref)
                            .update("debtor", User(name, phone, cardNumber, uri))
                    }
                }
            }
        )
    }

    private fun updateDebt(debt: Debt, extraDebt: Double) {
        database.collection("debts")
            .document(debt.ref)
            .update("debtSize", debt.debtSize + extraDebt)
    }

    private fun addDebt(debtSize: Double, debtors: ArrayList<User>) {
        val disposavle = getCurrentUser().subscribeBy(
            onSuccess = { currentUser ->
                val disposable = getUserDebtors().subscribeBy(
                    onSuccess = {
                        for (user in debtors) {
                            var flag = true
                            for (item in it) {
                                if (item.creditor.name == currentUser.name && user.name == item.debtor.name ||
                                    item.debtor.name == currentUser.name && user.name == item.creditor.name
                                ) {
                                    flag = false
                                    updateDebt(item, debtSize)
                                }
                            }
                            if (flag) {
                                if (user.name != currentUser.name) {
                                    val ref = database.collection("debts").document()
                                    val debt = Debt(
                                        ref.id,
                                        user,
                                        currentUser,
                                        debtSize,
                                        debtorApproval = false,
                                        creditorApproval = false
                                    )
                                    ref.set(debt)
                                }
                            }
                        }
                    }
                )
            })
    }

    fun addPurchase(
        partyId: String,
        title: String,
        price: Double,
        userList: ArrayList<User>,
        photo: Bitmap?,
        check: Bitmap?
    ) {
        val disposavle = getCurrentUser().subscribeBy(
            onSuccess = { user ->
                if (photo != null) {
                    val disposable = addImageToStorage(partyId, photo, "purchasePhoto").subscribeBy(
                        onSuccess = {
                            val downloadPhotoUrl = it
                            if (check != null) {
                                val disposable = addImageToStorage(partyId, check, "purchaseCheck").subscribeBy(
                                    onSuccess = { checkUrl ->
                                        val purchase =
                                            Purchase(title, price, user, userList, downloadPhotoUrl, checkUrl)
                                        database.collection("parties").document(partyId)
                                            .update("purchaseList", FieldValue.arrayUnion(purchase))
                                        addDebt(price / userList.count(), userList)
                                    })
                            }
                        })
                }
            }
        )


    }

    fun addUserToParty(partyRef: String) {
        val disposable = getCurrentUser().subscribeBy(
            onSuccess = {
                database.collection("parties").document(partyRef)
                    .update("users", FieldValue.arrayUnion(it))//вставка элемента в массив
            }
        )
    }

    fun addNewParty(
        title: String,
        description: String?,
        location: String,
        date: Date,
        password: String,
        bitmap: Bitmap?
    ) {
        val newPartyRef = database.collection("parties").document()
        if (bitmap != null) {
            val disposable = addImageToStorage(newPartyRef.id, bitmap, "userImages").subscribeBy(
                onSuccess = {
                    val downloadUrl = it
                    val users = ArrayList<User>()
                    val party = Party(
                        newPartyRef.id,
                        title,
                        description,
                        location,
                        date,
                        password,
                        users,
                        ArrayList(),
                        downloadUrl
                    )
                    newPartyRef.set(party)
                    addUserToParty(newPartyRef.id)

                })
        } else {
            val users = ArrayList<User>()
            val party = Party(
                newPartyRef.id,
                title,
                description,
                location,
                date,
                password,
                users,
                ArrayList(),
                null
            )
            newPartyRef.set(party)
            addUserToParty(newPartyRef.id)
        }
    }

    private fun updateUserInAllParties(name: String, phone: String, cardNumber: String?, uri: String?) {
        val disposable = getAllParties().subscribeBy(
            onSuccess = {
                val refToUpdate = ArrayList<DocumentReference>()
                for (party in it) {
                    var flag = false
                    for (_user in party.users)
                        if (_user.name == name)
                            flag = true
                    if (flag) {
                        val partyRef = party.ref ?: ""
                        database.collection("parties")
                            .document(partyRef)
                            .get().addOnSuccessListener { result ->
                                val _party = result.toObject(Party::class.java)
                                val userList = _party?.users
                                val newUserList = ArrayList<User>()
                                //

                                if (_party != null) {
                                    for (item in _party.users) {
                                        if (item.name == name)
                                            newUserList.add(User(name, phone, cardNumber, uri))
                                        else
                                            newUserList.add(item)
                                    }
                                }
                                val purchaseList = ArrayList<Purchase>()
                                if (_party != null) {
                                    for (purchase in _party.purchaseList) {
                                        val _userList = ArrayList<User>()
                                        for (user in purchase.userList) {
                                            if (user.name == name)
                                                _userList.add(User(name, phone, cardNumber, uri))
                                            else
                                                _userList.add(user)
                                        }
                                        purchaseList.add(
                                            Purchase(
                                                purchase.title, purchase.price, purchase.creditor, _userList,
                                                purchase.imageUrl, purchase.checkUrl
                                            )
                                        )
                                    }
                                }
                                database.collection("parties")
                                    .document(partyRef)
                                    .update("purchaseList", purchaseList)
                                database.collection("parties")
                                    .document(partyRef)
                                    .update("users", newUserList)
                            }
                    }
                }
            }
        )
    }

    fun updateCurrentUser(name: String, phone: String, cardNumber: String?, bitmap: Bitmap?) {

        val userId = sharedPreferences.getString(CURRENT_USER, "")
        val docRef = database.collection("users").document(userId)
        if (bitmap != null) {
            val disposable = addImageToStorage(userId, bitmap, "userImages").subscribeBy(onSuccess = {
                docRef.update(
                    mapOf(
                        "name" to name,
                        "phoneNumber" to phone,
                        "cardNumber" to cardNumber,
                        "imageUrl" to it
                    )
                )
                updateUserInAllParties(name, phone, cardNumber, it)
                updateUserInAllDebts(name, phone, cardNumber, it)
            })
        } else {
            docRef.update(
                mapOf(
                    "name" to name,
                    "phoneNumber" to phone,
                    "cardNumber" to cardNumber
                )
            )
            updateUserInAllParties(name, phone, cardNumber, null)
            updateUserInAllDebts(name, phone, cardNumber, null)
        }
    }

    fun getAllParties(): Maybe<ArrayList<Party>> =
        Maybe.create { emitter ->
            database.collection("parties")
                .get()
                .addOnSuccessListener { result ->
                    val partyList: ArrayList<Party> = ArrayList()
                    for (document in result) {
                        partyList.add(document.toObject(Party::class.java))
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                    emitter.onSuccess(partyList)
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                    emitter.onError(exception)
                }
        }

    fun getParty(partyId: String): Maybe<Party> =
        Maybe.create { emitter ->
            database.collection("parties").document(partyId).get()
                .addOnSuccessListener { documentSnapshot ->
                    val party = documentSnapshot.toObject(Party::class.java)
                    party?.let { emitter.onSuccess(it) }
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }

    fun getPurchase(partyId: String, title: String): Maybe<Purchase> =
        Maybe.create { emitter ->
            database.collection("parties")
                .document(partyId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val party = documentSnapshot.toObject(Party::class.java)
                    if (party != null) {
                        for (purchase in party.purchaseList)
                            if (purchase.title == title)
                                emitter.onSuccess(purchase)
                    }
                }
        }

    fun getPurchase(partyId: String): Maybe<ArrayList<Purchase>> =
        Maybe.create { emitter ->
            database.collection("parties")
                .document(partyId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val party = documentSnapshot.toObject(Party::class.java)
                    val purchaseList: ArrayList<Purchase> = party?.purchaseList as ArrayList<Purchase>
                    emitter.onSuccess(purchaseList)
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                    emitter.onError(exception)
                }
        }

    fun getUserParties(): Maybe<ArrayList<Party>> {
        return Maybe.create { emitter ->
            val userId = sharedPreferences.getString(CURRENT_USER, "")
            val docRef = database.collection("users").document(userId)
            docRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    val user = documentSnapshot.toObject(User::class.java)
                    val userName = user?.name
                    val disposable = getAllParties().subscribeBy(
                        onSuccess = {
                            val nessaryParty = ArrayList<Party>()
                            for (party in it)
                                for (_user in party.users)
                                    if (_user.name == userName)
                                        nessaryParty.add(party)
                            emitter.onSuccess(nessaryParty)
                        }
                    )
                }
        }
    }

    fun addNewUser(name: String, phone: String, cardNumber: String?, imgUrl: String?) {
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

    fun getCurrentUser(): Maybe<User> =
        Maybe.create { emitter ->
            val userId = sharedPreferences.getString(CURRENT_USER, "")
            val docRef = database.collection("users").document(userId)
            docRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    val user = documentSnapshot.toObject(User::class.java)
                    user?.let { emitter.onSuccess(it) }
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }

    companion object {
        const val CURRENT_USER = "current_user_id"
    }

}
