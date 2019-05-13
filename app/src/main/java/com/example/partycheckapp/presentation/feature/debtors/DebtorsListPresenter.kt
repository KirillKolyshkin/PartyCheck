package com.example.partycheckapp.presentation.feature.debtors

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.data.debtors.Debt
import com.example.partycheckapp.data.debtors.UserDebtor
import com.example.partycheckapp.data.user.User
import com.example.partycheckapp.domain.bd.DBProvider
import com.example.partycheckapp.presentation.feature.party.view.DebtorsListView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class DebtorsListPresenter(private val dbProvider: DBProvider) : MvpPresenter<DebtorsListView>() {
    private val compositeDisposable = CompositeDisposable()
    private var user = User()

    fun setUsersDebtorsList() {
        val disposable = dbProvider.getUserDebtors().subscribeBy(
            onSuccess = {
                viewState.getDebtorsList(parseDebtToUserDebtor(it))
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

    private fun parseDebtToUserDebtor(debts: ArrayList<Debt>): ArrayList<UserDebtor> {
        val debtorsList = ArrayList<UserDebtor>()
        for (debt in debts) {
            if (debt.creditor.name == user.name)
                debtorsList.add(
                    UserDebtor(
                        debt.debtor.name,
                        debt.debtor.phoneNumber,
                        debt.debtor.cardNumber,
                        debt.debtSize,
                        debt.debtor.imageUrl,
                        debt.ref
                    )
                )
            else
                debtorsList.add(
                    UserDebtor(
                        debt.creditor.name,
                        debt.creditor.phoneNumber,
                        debt.creditor.cardNumber,
                        debt.debtSize * -1,
                        debt.creditor.imageUrl,
                        debt.ref
                    )
                )
        }
        return debtorsList
    }

    override fun destroyView(view: DebtorsListView?) {
        super.destroyView(view)
        compositeDisposable.clear()
    }
}