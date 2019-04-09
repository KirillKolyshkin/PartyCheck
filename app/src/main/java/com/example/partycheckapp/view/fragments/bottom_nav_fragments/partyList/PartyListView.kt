package com.example.partycheckapp.view.fragments.bottom_nav_fragments.partyList

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.partycheckapp.entity.Party

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface PartyListView : MvpView {
    fun showPartyList(dataList: ArrayList<Party>)
}
