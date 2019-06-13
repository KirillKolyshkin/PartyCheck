package com.example.partycheckapp.presentation.feature.partydetails.purchaselist

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.bd.DBProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class PurchaseListPresenter(private val dbProvider: DBProvider) : MvpPresenter<PurchaseListView>() {
    private val compositeDisposable = CompositeDisposable()

    fun showPurchase(partyId: String) {
        val disposable = dbProvider.getPurchase(partyId)
            .subscribeBy(onSuccess = {
                if (it.any())
                    viewState.showPurchaseList(it)
            })
        compositeDisposable.add(disposable)
    }

    override fun destroyView(view: PurchaseListView?) {
        super.destroyView(view)
        compositeDisposable.clear()
    }
}
