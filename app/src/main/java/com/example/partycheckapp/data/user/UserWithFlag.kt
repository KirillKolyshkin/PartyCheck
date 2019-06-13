package com.example.partycheckapp.data.user

data class UserWithFlag(
    val name: String = "",
    val phoneNumber: String = "",
    val cardNumber: String? = null,
    val imageUrl: String? = null,
    var flag: Boolean = false
)
