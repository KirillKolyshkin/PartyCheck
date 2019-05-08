package com.example.partycheckapp.presentation.feature.debtorDetails

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.bd.DBProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class DebtorDetailsPresenter(private val dbProvider: DBProvider) : MvpPresenter<DebtorDetailsView>() {
    protected val compositeDisposable = CompositeDisposable()

    fun getDebt(debtRef: String) {
        val disposable = dbProvider.getDebt(debtRef).subscribeBy(
            onSuccess = {
                viewState.getDebt(it)
            }
        )
        compositeDisposable.add(disposable)
    }

    fun getUser() {
        val disposable = dbProvider.getCurrentUser().subscribeBy(
            onSuccess = {
                viewState.getUser(it)
            }
        )
        compositeDisposable.add(disposable)
    }

    fun updateDebt(debtRef: String, boolean: Boolean) = dbProvider.updateDebt(debtRef, boolean)

    override fun destroyView(view: DebtorDetailsView?) {
        super.destroyView(view)
        compositeDisposable.clear()
    }
}
