package com.example.partycheckapp.presentation.feature.auth

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface SignInView : MvpView {
    fun checkUser ()
    fun confirmAuth()
    fun showDialog()
}
