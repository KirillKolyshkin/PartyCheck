package com.example.partycheckapp.presenter

import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.model.PartyModel
import com.example.partycheckapp.view.fragments.bottom_nav_fragments.partyList.PartyListView

class PartyListPresenter (private val model: PartyModel) : MvpPresenter<PartyListView>() {

    fun setPartyList() =
        viewState.showPartyList(model.getTestParty())

    //fun openDate(int: Int) =
        //router.navigateTo(Screens.DateDetailScreen(int))
}