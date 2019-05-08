package com.example.partycheckapp.data.user

import com.example.partycheckapp.data.party.Party

data class UserWithFlag(
    val name: String,
    val phoneNumber: String,
    val cardNumber: String?,
    val imageUrl: String?,
    var flag: Boolean
) {
    constructor() : this("", "", null, null, false)
}
