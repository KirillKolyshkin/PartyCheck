package com.example.partycheckapp.presentation.feature.debtors

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.bd.DBProvider
import com.example.partycheckapp.presentation.feature.party.view.DebtorsListView
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class DebtorsListPresenter(private val dbProvider: DBProvider) : MvpPresenter<DebtorsListView>() {

    fun setUsersDebtorsList() = dbProvider.getUserDebtors().subscribeBy(
        onSuccess = {
            viewState.showDebtorsList(it)
        }
    )

    fun getCurrentUser() = dbProvider.getCurrentUser().subscribeBy(
        onSuccess = {
            viewState.getUser(it)
            setUsersDebtorsList()
        }
    )
}