package com.example.partycheckapp.presentation.feature.debtors

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.data.debtors.Debt
import com.example.partycheckapp.data.debtors.UserDebtor
import com.example.partycheckapp.data.user.User
import com.example.partycheckapp.domain.bd.DBProvider
import com.example.partycheckapp.domain.debtor.DebtorMapper
import com.example.partycheckapp.presentation.feature.party.view.DebtorsListView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class DebtorsListPresenter(private val dbProvider: DBProvider, private val mapper: DebtorMapper) : MvpPresenter<DebtorsListView>() {
    private val compositeDisposable = CompositeDisposable()
    private var user = User()

    fun setUsersDebtorsList() {
        val disposable = dbProvider.getUserDebtors().subscribeBy(
            onSuccess = {
                viewState.getDebtorsList(mapper.parseDebtToUserDebtor(it, user.name))
            }
        )
        compositeDisposable.add(disposable)
    }

    fun getCurrentUser() {
        val disposable = dbProvider.getCurrentUser().subscribeBy(
            onSuccess = {
                user = it
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
