package com.example.partycheckapp.presentation.feature.auth.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.bd.DBProvider
import com.example.partycheckapp.domain.user.UserInteractor
import com.example.partycheckapp.presentation.feature.auth.view.SignInView
import io.reactivex.rxkotlin.subscribeBy


@InjectViewState
class SignInPresenter(private val interactor: UserInteractor, private val provider: DBProvider) : MvpPresenter<SignInView>() {

    fun sentCode(phone: String) = interactor.sentCode(phone)
        .subscribeBy(onComplete = {
            viewState.showDialog()
        }, onSuccess = {
            viewState.confirmAuth()
        }, onError = {

        })

    fun addUserToDB(name: String, phone: String) = provider.addNewUser(name,phone, null, null)
    fun getUser() = interactor.getUser()
    fun verifyPhone(code: String) = interactor.verifyPhoneNumber(code)
}