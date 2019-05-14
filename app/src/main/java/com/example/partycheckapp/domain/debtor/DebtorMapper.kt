package com.example.partycheckapp.domain.debtor

import com.example.partycheckapp.data.debtors.Debt
import com.example.partycheckapp.data.debtors.UserDebtor

class DebtorMapper {
    fun parseDebtToUserDebtor(debts: ArrayList<Debt>, userName: String): ArrayList<UserDebtor> {
        val debtorsList = ArrayList<UserDebtor>()
        for (debt in debts) {
            if (debt.creditor.name == userName)
                debtorsList.add(
                    UserDebtor(
                        debt.debtor.name,
                        debt.debtor.phoneNumber,
                        debt.debtor.cardNumber,
                        debt.debtSize,
                        debt.debtor.imageUrl,
                        debt.ref
                    )
                )
            else
                debtorsList.add(
                    UserDebtor(
                        debt.creditor.name,
                        debt.creditor.phoneNumber,
                        debt.creditor.cardNumber,
                        debt.debtSize * -1,
                        debt.creditor.imageUrl,
                        debt.ref
                    )
                )
        }
        return debtorsList
    }
}