package com.example.partycheckapp.presentation.feature.profile

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.partycheckapp.data.user.User

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ProfileView : MvpView {
    fun setUser(user: User?)
    fun showDialog()
}
