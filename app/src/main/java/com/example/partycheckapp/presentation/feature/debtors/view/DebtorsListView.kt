package com.example.partycheckapp.presentation.feature.party.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.partycheckapp.data.debtors.UserDebtor
import com.example.partycheckapp.data.party.Party

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface DebtorsListView : MvpView {
    fun showDebtorsList(dataList: ArrayList<UserDebtor>)
}
