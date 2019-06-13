package com.example.partycheckapp.data.party

import com.example.partycheckapp.data.user.User

data class Purchase(
    val title: String = "",
    val price: Double = 0.0,
    val creditor: User = User(),
    val userList: List<User> = ArrayList(),
    val imageUrl: String? = null,
    val checkUrl: String? = null
)
