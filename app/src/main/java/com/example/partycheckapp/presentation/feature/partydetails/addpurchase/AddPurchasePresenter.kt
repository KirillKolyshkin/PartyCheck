package com.example.partycheckapp.presentation.feature.partydetails.addpurchase

import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.data.user.User
import com.example.partycheckapp.domain.bd.DBProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class AddPurchasePresenter(private val dbProvider: DBProvider) : MvpPresenter<AddPurchaseView>() {
    private val compositeDisposable = CompositeDisposable()

    fun getParty(partyId: String) {
        val disposable = dbProvider.getParty(partyId)
            .subscribeBy(onSuccess = {
                viewState.getParty(it)
            })
        compositeDisposable.add(disposable)
    }

    fun addPurchase(
        partyId: String,
        title: String,
        price: Double,
        userList: ArrayList<User>,
        photo: Bitmap?,
        check: Bitmap?
    ) = dbProvider.addPurchase(partyId, title, price, userList, photo, check)

    override fun destroyView(view: AddPurchaseView?) {
        super.destroyView(view)
        compositeDisposable.clear()
    }
}
