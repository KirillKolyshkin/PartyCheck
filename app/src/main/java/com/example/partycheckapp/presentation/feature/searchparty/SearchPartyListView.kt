package com.example.partycheckapp.presentation.feature.searchparty

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.partycheckapp.data.party.Party

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface SearchPartyListView : MvpView {
    fun showPartyList(dataList: ArrayList<Party>)
    fun getUserPartyList(dataList: ArrayList<Party>)
}
