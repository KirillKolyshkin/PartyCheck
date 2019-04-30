package com.example.partycheckapp.data.user

import com.example.partycheckapp.data.party.Party

data class User(
    val name: String,
    val phoneNumber: String,
    val cardNumber: Long?,
    val imageUrl: String?,
    val parties: List<Party>
) {
    constructor() : this("", "", null, null, ArrayList<Party>())
}