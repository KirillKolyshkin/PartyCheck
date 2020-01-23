package com.example.partycheckapp.presentation.feature.searchparty

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.bd.DBProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class SearchPartyListPresenter(private val dbProvider: DBProvider) : MvpPresenter<SearchPartyListView>() {
    private val compositeDisposable = CompositeDisposable()

    fun setPartySearchList() {
        val disposable = dbProvider.getAllParties().subscribeBy(
            onSuccess = {
                viewState.showPartyList(it)
            }
        )
        compositeDisposable.add(disposable)
    }

    fun getUserPartyList() {
        val disposable = dbProvider.getUserParties().subscribeBy(
            onSuccess = {
                viewState.getUserPartyList(it)
            }
        )
        compositeDisposable.add(disposable)
    }

    fun addUserToParty(partyId: String) = dbProvider.addUserToParty(partyId)

    override fun destroyView(view: SearchPartyListView?) {
        super.destroyView(view)
        compositeDisposable.clear()
    }
}
