package com.example.partycheckapp.presentation.feature.party.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.party.PartyModel
import com.example.partycheckapp.presentation.feature.party.view.PartyListView

@InjectViewState
class PartyListPresenter : MvpPresenter<PartyListView>() {

    fun setPartyList() = viewState.showPartyList(PartyModel().getTestParty())

    //fun openDate(int: Int) =
        //router.navigateTo(Screens.DateDetailScreen(int))
}