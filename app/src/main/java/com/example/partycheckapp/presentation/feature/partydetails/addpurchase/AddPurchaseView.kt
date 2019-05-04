package com.example.partycheckapp.presentation.feature.partydetails.addpurchase

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.partycheckapp.data.party.Party

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface AddPurchaseView: MvpView {
    fun showDialog(isIcon: Boolean)
    fun getParty(party: Party)
}
