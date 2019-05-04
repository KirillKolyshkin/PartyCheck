package com.example.partycheckapp.presentation.feature.partydetails.mainpartyscreen

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.partycheckapp.data.party.Party

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface MainPartyScreenView:MvpView {
    fun initView(party: Party)
}