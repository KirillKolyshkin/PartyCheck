package com.example.partycheckapp.presentation.feature.auth.fragment.sign_in.presenter

import android.app.Activity
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.user.UserModel
import com.example.partycheckapp.presentation.feature.auth.fragment.sign_in.view.SignInView


@InjectViewState
class SignInPresenter(private val model: UserModel) : MvpPresenter<SignInView>() {

    fun authPassed() = viewState.confirmAuth()
    fun sentCode(phone: String, activity: Activity) = model.sentCode(phone, activity)
    fun getUser() = model.getUser()
    fun tryToPassAuth(activity: Activity) = model.tryToEnterWithoutAuth(activity)
    fun verifyPhone(code:String, activity: Activity) = model.verifyPhoneNumber(code, activity)
}