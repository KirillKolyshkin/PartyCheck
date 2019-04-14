package com.example.partycheckapp.data.debtors

data class Debtor (
    val id: Long,
    val creditorId: Long,
    val debtorId: Long,
    val debtSize: Double
)