package com.example.partycheckapp.presentation.feature.partydetails.purchasedetails

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.partycheckapp.data.party.Purchase

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface PurchaseDetailsView: MvpView {
    fun initPurchase(purchase: Purchase)
}
