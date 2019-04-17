package com.example.partycheckapp.presentation.feature.searchParty.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.party.PartyModel
import com.example.partycheckapp.presentation.feature.searchParty.view.SearchPartyListView

@InjectViewState
class PartyListPresenter : MvpPresenter<SearchPartyListView>() {

    fun setPartySearchList() = viewState.showPartyList(PartyModel().getTestSearchParty())

}