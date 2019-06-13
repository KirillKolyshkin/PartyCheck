package com.example.partycheckapp.data.debtors

data class UserDebtor (
    val name: String,
    val phoneNumber: String,
    val cardNumber: String?,
    val debtSize: Double,
    val imageUrl: String?,
    val debtRef: String
)