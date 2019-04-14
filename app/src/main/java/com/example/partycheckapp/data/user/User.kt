package com.example.partycheckapp.data.user

data class User (
    val id: Long,
    val name: String,
    val phoneNumber: Long?,
    val cardNumber: Long?,
    val found: Boolean,
    val debitorList: ArrayList<Long>,
    val partyList: ArrayList<Long>,
    val image: ByteArray
)