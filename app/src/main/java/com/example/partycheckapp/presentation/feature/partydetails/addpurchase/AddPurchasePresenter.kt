package com.example.partycheckapp.presentation.feature.partydetails.addpurchase

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.bd.DBProvider
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class AddPurchasePresenter(private val dbProvider: DBProvider): MvpPresenter<AddPurchaseView>() {
    fun getParty(partyId: String) = dbProvider.getParty(partyId)
        .subscribeBy(onSuccess = {
            viewState.getParty(it)
        })
}
