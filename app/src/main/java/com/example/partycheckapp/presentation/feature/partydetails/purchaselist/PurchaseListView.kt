package com.example.partycheckapp.presentation.feature.partydetails.purchaselist

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.partycheckapp.data.party.Purchase

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface PurchaseListView: MvpView {
    fun showPurchaseList(dataList: ArrayList<Purchase>)
}
