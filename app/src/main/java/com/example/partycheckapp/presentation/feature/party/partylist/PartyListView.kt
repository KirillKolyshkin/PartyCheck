package com.example.partycheckapp.presentation.feature.party.partylist

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.partycheckapp.data.party.Party

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface PartyListView : MvpView {
    fun showPartyList(dataList: ArrayList<Party>)
}
