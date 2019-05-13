package com.example.partycheckapp.data.debtors

import com.example.partycheckapp.data.user.User

data class Debt(
    val ref: String = "",
    val debtor: User = User(),
    val creditor: User = User(),
    val debtSize: Double = 0.0,
    var debtorApproval: Boolean = false,
    var creditorApproval: Boolean = false
)
