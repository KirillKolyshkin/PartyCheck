package com.example.partycheckapp.data.party

import java.util.*

data class PartyWithDebt (
    val id: Long,
    val title: String,
    val date: Date,
    val organization: String,
    val image: ByteArray?,
    val debtSize: Double
)