package com.example.partycheckapp.data.party

import android.os.Parcelable
import com.example.partycheckapp.data.user.User
import java.util.*
import kotlin.collections.ArrayList

data class Party(
    val ref: String?,
    val title: String,
    val description: String?,
    val location: String,
    val date: Date,
    val password: String,
    val users: List<User>,
    val purchaseList: List<Purchase>,
    val imageUrl: String?
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        Date(),
        "",
        ArrayList<User>(),
        ArrayList<Purchase>(),
        null)
}
