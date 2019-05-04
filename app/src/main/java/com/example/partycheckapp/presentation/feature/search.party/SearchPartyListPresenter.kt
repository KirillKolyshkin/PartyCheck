package com.example.partycheckapp.presentation.feature.search.party

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.bd.DBProvider
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class SearchPartyListPresenter(private val dbProvider: DBProvider) : MvpPresenter<SearchPartyListView>() {

    fun setPartySearchList() = dbProvider.getAllParties().subscribeBy(
        onSuccess = {
            viewState.showPartyList(it)
        }
    )
}
