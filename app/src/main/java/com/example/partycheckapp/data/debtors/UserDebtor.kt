package com.example.partycheckapp.data.debtors

data class UserDebtor (
    val id: Long,
    val name: String,
    val phoneNumber: Long?,
    val cardNumber: Long?,
    val debtSize: Double
)