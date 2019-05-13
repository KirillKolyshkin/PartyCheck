package com.example.partycheckapp.presentation.feature.partydetails.purchasedetails

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.bd.DBProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class PurchaseDetailsPresenter(private val dbProvider: DBProvider) : MvpPresenter<PurchaseDetailsView>() {
    private val compositeDisposable = CompositeDisposable()

    fun getPurchase(partyId: String, title: String) {
        val disposable = dbProvider.getPurchase(partyId, title)
            .subscribeBy(
                onSuccess = {
                    viewState.initPurchase(it)
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun destroyView(view: PurchaseDetailsView?) {
        super.destroyView(view)
        compositeDisposable.clear()
    }
}
