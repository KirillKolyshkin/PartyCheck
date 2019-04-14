package com.example.partycheckapp.presentation.feature.debtors.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.party.PartyModel
import com.example.partycheckapp.domain.user.UserModel
import com.example.partycheckapp.presentation.feature.party.view.DebtorsListView
import com.example.partycheckapp.presentation.feature.party.view.PartyListView

@InjectViewState
class DebtorsListPresenter : MvpPresenter<DebtorsListView>() {

    fun setUsersDebtorsList() = viewState.showDebtorsList(UserModel().getTestDebtors())

    //fun openDate(int: Int) =
        //router.navigateTo(Screens.DateDetailScreen(int))
}