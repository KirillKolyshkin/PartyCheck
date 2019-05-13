package com.example.partycheckapp.presentation.feature.debtorDetails

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.partycheckapp.data.debtors.Debt
import com.example.partycheckapp.data.user.User

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface DebtorDetailsView : MvpView {
    fun receiveDebt(debt: Debt)
}
