package com.example.partycheckapp.domain.party

import com.example.partycheckapp.data.party.Party
import com.example.partycheckapp.data.party.PartyWithDebt
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class PartyModel {

    fun getParty(): Party {

        val list = ArrayList<Long>()
        for (item: Int in 1..3)
            list.add(item.toLong())

        return Party(
            1, "MyPartyTest", "SomeDescription", "Itis", Date(12272000), "a", "best people",
            list, list, null
        )
    }

    fun getDebtParty(): PartyWithDebt{
        return PartyWithDebt(1, "DebtParty", Date(12272000),"Itis", null, Random.nextDouble(-100.0, 100.0))
    }


    fun getTestDebtParty(): ArrayList<PartyWithDebt>{
        val list = java.util.ArrayList<PartyWithDebt>()
        for (item: Int in 1..10) {
            list.add(getDebtParty())
        }
        return list
    }

    fun getTestSearchParty(): ArrayList<Party> {
        val list = java.util.ArrayList<Party>()
        for (item: Int in 1..10) {
            list.add(getParty())
        }
        return list
    }
}