package com.example.partycheckapp.entity

data class Purchase(
    val id: Long,
    val title: String,
    val price: Int,
    val userList: ArrayList<Long>,
    val image: ByteArray,
    val check: ByteArray
)