package com.example.partycheckapp.data.user

import com.example.partycheckapp.data.party.Party

data class UserWithFlag(
    val name: String,
    val phoneNumber: String,
    val cardNumber: Long?,
    val imageUrl: String?,
    val parties: List<Party>,
    var flag: Boolean
) {
    constructor() : this("", "", null, null, ArrayList<Party>(), false)
}
