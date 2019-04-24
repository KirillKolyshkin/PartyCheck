package com.example.partycheckapp.presentation.feature.debtors.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.user.UserInteractor
import com.example.partycheckapp.presentation.feature.party.view.DebtorsListView

@InjectViewState
class DebtorsListPresenter(private val userInteractor: UserInteractor) : MvpPresenter<DebtorsListView>() {

    fun setUsersDebtorsList() = viewState.showDebtorsList(userInteractor.getTestDebtors())

    //fun openDate(int: Int) =
        //router.navigateTo(Screens.DateDetailScreen(int))
}