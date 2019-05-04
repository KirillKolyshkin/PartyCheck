package com.example.partycheckapp.presentation.feature.partydetails.mainpartyscreen

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.bd.DBProvider
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class MainPartyScreenPresenter(private val dbProvider: DBProvider): MvpPresenter<MainPartyScreenView>() {
    fun getParty(partyId: String) = dbProvider.getParty(partyId)
        .subscribeBy(onSuccess = {
        viewState.initView(it)
    })
}