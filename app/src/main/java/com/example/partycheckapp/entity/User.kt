package com.example.partycheckapp.entity

data class User (
    val id: Long,
    val name: String,
    val phoneNumber: Int?,
    val cardNumber: Int?,
    val found: Boolean,
    val debitorList: ArrayList<Long>,
    val partyList: ArrayList<Long>,
    val image: ByteArray
)