package com.example.partycheckapp.data.debtors

import com.example.partycheckapp.data.user.User

data class Debt(
    val ref: String,
    val debtor: User,
    val creditor: User,
    val debtSize: Double,
    var debtorApproval: Boolean,
    var creditorApproval: Boolean
) {
    constructor() : this(
        "", User(), User(), 0.0, false, false
    )
}