package com.example.partycheckapp.presentation.feature.debtors

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.bd.DBProvider
import com.example.partycheckapp.presentation.feature.party.view.DebtorsListView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class DebtorsListPresenter(private val dbProvider: DBProvider) : MvpPresenter<DebtorsListView>() {
    protected val compositeDisposable = CompositeDisposable()

    fun setUsersDebtorsList() {
        val disposable = dbProvider.getUserDebtors().subscribeBy(
            onSuccess = {
                viewState.showDebtorsList(it)
            }
        )
        compositeDisposable.add(disposable)
    }

    fun getCurrentUser() {
        val disposable = dbProvider.getCurrentUser().subscribeBy(
            onSuccess = {
                viewState.getUser(it)
                setUsersDebtorsList()
            }
        )
        compositeDisposable.add(disposable)
    }

    override fun destroyView(view: DebtorsListView?) {
        super.destroyView(view)
        compositeDisposable.clear()
    }
}