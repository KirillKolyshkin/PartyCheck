package com.example.partycheckapp.presentation.feature.debtordetails

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.partycheckapp.data.debtors.Debt

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface DebtorDetailsView : MvpView {
    fun receiveDebt(debt: Debt)
}
