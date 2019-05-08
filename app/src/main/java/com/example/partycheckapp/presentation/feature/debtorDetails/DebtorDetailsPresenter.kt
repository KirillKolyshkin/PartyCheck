package com.example.partycheckapp.presentation.feature.debtorDetails

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.bd.DBProvider
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class DebtorDetailsPresenter(private val dbProvider: DBProvider) : MvpPresenter<DebtorDetailsView>() {
    fun getDebt(debtRef:String) = dbProvider.getDebt(debtRef).subscribeBy(
        onSuccess = {
            viewState.getDebt(it)
        }
    )

    fun getUser() = dbProvider.getCurrentUser().subscribeBy(
        onSuccess = {
            viewState.getUser(it)
        }
    )

    fun updateDebt(debtRef: String, boolean: Boolean) = dbProvider.updateDebt(debtRef, boolean)
}
