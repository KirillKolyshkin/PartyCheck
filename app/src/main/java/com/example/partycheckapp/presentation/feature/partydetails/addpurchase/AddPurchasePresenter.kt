package com.example.partycheckapp.presentation.feature.partydetails.addpurchase

import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.data.user.User
import com.example.partycheckapp.domain.bd.DBProvider
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class AddPurchasePresenter(private val dbProvider: DBProvider) : MvpPresenter<AddPurchaseView>() {
    fun getParty(partyId: String) = dbProvider.getParty(partyId)
        .subscribeBy(onSuccess = {
            viewState.getParty(it)
        })

    fun addPurchase(
        partyId: String,
        title: String,
        price: Double,
        userList: ArrayList<User>,
        photo: Bitmap?,
        check: Bitmap?
    ) = dbProvider.addPurchase(partyId, title, price, userList, photo, check)
}
