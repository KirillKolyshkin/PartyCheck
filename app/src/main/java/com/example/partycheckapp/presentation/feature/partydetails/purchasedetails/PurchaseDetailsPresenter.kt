package com.example.partycheckapp.presentation.feature.partydetails.purchasedetails

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.bd.DBProvider
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class PurchaseDetailsPresenter(private val dbProvider: DBProvider) : MvpPresenter<PurchaseDetailsView>() {

    fun getPurchase(partyId: String, title: String) = dbProvider.getPurchase(partyId, title)
        .subscribeBy(
        onSuccess = {
            viewState.initPurchase(it)
        }
    )
}
