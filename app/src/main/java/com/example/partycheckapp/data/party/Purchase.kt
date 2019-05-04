package com.example.partycheckapp.data.party

import com.example.partycheckapp.data.user.User

data class Purchase(
    val ref: String?,
    val title: String,
    val price: Double,
    val creditor: User,
    val userList: List<User>,
    val imageUrl: String?,
    val checkUrl: String?
) {
    constructor() : this(
        "",
        "title",
        0.0,
        User(),
        ArrayList<User>(),
        null,
        null
    )
}