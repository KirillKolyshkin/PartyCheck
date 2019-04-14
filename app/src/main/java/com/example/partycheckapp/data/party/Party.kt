package com.example.partycheckapp.data.party

import java.util.*
import kotlin.collections.ArrayList

data class Party (
    val id: Long,
    val title: String,
    val description: String?,
    val location: String,
    val date: Date,
    val password: String,
    val organization: String,
    val participantList: ArrayList<Long>,
    val purchaseList: ArrayList<Long>,
    val image: ByteArray?
)