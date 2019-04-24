package com.example.partycheckapp.data.user

data class User(
    val name: String,
    val phoneNumber: String,
    val cardNumber: Long?,
    val imageUrl: String?
) {
    constructor() : this("", "", null, null)
}