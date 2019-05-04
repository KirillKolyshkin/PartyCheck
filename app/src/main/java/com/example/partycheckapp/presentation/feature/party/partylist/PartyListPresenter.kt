package com.example.partycheckapp.presentation.feature.party.partylist

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.bd.DBProvider
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class PartyListPresenter(private val dbProvider: DBProvider) : MvpPresenter<PartyListView>() {

    fun setMyPartyList() = dbProvider.getUserParties().subscribeBy(
        onSuccess = {
            viewState.showPartyList(it)
        }
    )
}
