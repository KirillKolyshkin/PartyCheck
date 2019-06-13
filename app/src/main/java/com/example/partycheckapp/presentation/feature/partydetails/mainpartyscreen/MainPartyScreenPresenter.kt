package com.example.partycheckapp.presentation.feature.partydetails.mainpartyscreen

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.bd.DBProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class MainPartyScreenPresenter(private val dbProvider: DBProvider) : MvpPresenter<MainPartyScreenView>() {
    private val compositeDisposable = CompositeDisposable()

    fun getParty(partyId: String) {
        val disposable = dbProvider.getParty(partyId)
            .subscribeBy(onSuccess = {
                viewState.initView(it)
            })
        compositeDisposable.add(disposable)
    }

    override fun destroyView(view: MainPartyScreenView?) {
        super.destroyView(view)
        compositeDisposable.clear()
    }
}