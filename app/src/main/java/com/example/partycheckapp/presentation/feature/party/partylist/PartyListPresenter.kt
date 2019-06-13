package com.example.partycheckapp.presentation.feature.party.partylist

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.bd.DBProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class PartyListPresenter(private val dbProvider: DBProvider) : MvpPresenter<PartyListView>() {
    private val compositeDisposable = CompositeDisposable()

    fun setMyPartyList() {
        val disposable = dbProvider.getUserParties().subscribeBy(
            onSuccess = {
                viewState.showPartyList(it)
            }
        )
        compositeDisposable.add(disposable)
    }

    override fun destroyView(view: PartyListView?) {
        super.destroyView(view)
        compositeDisposable.clear()
    }
}
