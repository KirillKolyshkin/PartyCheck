package com.example.partycheckapp.data.party

data class Purchase(
    val id: Long,
    val title: String,
    val price: Double,
    val userList: ArrayList<Long>,
    val image: ByteArray,
    val check: ByteArray
)