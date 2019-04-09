package com.example.partycheckapp.model

import com.example.partycheckapp.entity.Party
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PartyModel {

    fun getParty(): Party {

        val list = ArrayList<Long>()
        for (item: Int in 1..3)
            list.add(item as Long)

        return Party(
            1, "MyPartyTest", "SomeDescription", "Itis", Date(12272000), "a", "best people",
            list, list, null
        )
    }

    fun getTestParty(): ArrayList<Party> {
        val list = java.util.ArrayList<Party>()
        for (item: Int in 1..10) {
            list.add(getParty())
        }
        return list
    }
}