package com.example.partycheckapp.presentation.feature.auth

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.bd.DBProvider
import com.example.partycheckapp.domain.user.UserInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class SignInPresenter(private val interactor: UserInteractor, private val provider: DBProvider) :
    MvpPresenter<SignInView>() {
    private val compositeDisposable = CompositeDisposable()

    fun sentCode(phone: String) {
        val disposable = interactor.sentCode(phone)
            .subscribeBy(onComplete = {
                viewState.showDialog()
            }, onSuccess = {
                viewState.confirmAuth()
            }, onError = {

            })
        compositeDisposable.add(disposable)
    }

    fun addUserToDB(name: String, phone: String) = provider.addNewUser(name, phone, null, null)
    fun getUser() = interactor.getUser()
    fun verifyPhone(code: String) = interactor.verifyPhoneNumber(code)

    override fun destroyView(view: SignInView?) {
        super.destroyView(view)
        compositeDisposable.clear()
    }
}
