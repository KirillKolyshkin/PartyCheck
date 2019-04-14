package com.example.partycheckapp.domain.user

import com.example.partycheckapp.data.debtors.UserDebtor
import kotlin.random.Random

class UserModel {

    fun getDebtor(): UserDebtor {

        return UserDebtor(
            1, "UserName", 88005553535, 5555,Random.nextDouble(-100.0, 100.0)
        )
    }

    fun getTestDebtors(): ArrayList<UserDebtor>{
        val list = java.util.ArrayList<UserDebtor>()
        for (item: Int in 1..10) {
            list.add(getDebtor())
        }
        return list
    }
}