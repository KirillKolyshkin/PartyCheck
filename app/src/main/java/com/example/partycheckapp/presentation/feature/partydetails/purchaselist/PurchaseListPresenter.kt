package com.example.partycheckapp.presentation.feature.partydetails.purchaselist

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.bd.DBProvider
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class PurchaseListPresenter(private val dbProvider: DBProvider) : MvpPresenter<PurchaseListView>() {
    fun showPurchase(partyId: String) = dbProvider.getPurchase(partyId)
        .subscribeBy(onSuccess = {
            if (it.any())
                viewState.showPurchaseList(it)
        })
}
