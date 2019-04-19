package com.example.partycheckapp.presentation.feature.auth.fragment.sign_in.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.partycheckapp.data.party.PartyWithDebt


@StateStrategyType(value = AddToEndSingleStrategy::class)
interface SignInView : MvpView {
    fun addUser ()
    fun confirmAuth()
}