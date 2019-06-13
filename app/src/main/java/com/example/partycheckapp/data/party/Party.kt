package com.example.partycheckapp.data.party

import com.example.partycheckapp.data.user.User
import java.util.Date
import kotlin.collections.ArrayList

data class Party(
    val ref: String? = "",
    val title: String = "",
    val description: String? = "",
    val location: String = "",
    val date: Date = Date(),
    val password: String = "",
    val users: List<User> = ArrayList(),
    val purchaseList: List<Purchase> = ArrayList(),
    val imageUrl: String? = null
)
