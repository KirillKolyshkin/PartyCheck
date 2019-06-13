package com.example.partycheckapp.presentation.feature.profile

import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.bd.DBProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class ProfilePresenter(private val dbProvider: DBProvider) : MvpPresenter<ProfileView>() {
    private val compositeDisposable = CompositeDisposable()

    fun getUser() {
        val disposable = dbProvider.getCurrentUser()
            .subscribeBy(onSuccess = {
                viewState.setUser(it)
            })
        compositeDisposable.add(disposable)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getUser()
    }

    fun updateUser(name: String, phone: String, cardNumber: String?, bitmap: Bitmap?) =
        dbProvider.updateCurrentUser(name, phone, cardNumber, bitmap)

    override fun destroyView(view: ProfileView?) {
        super.destroyView(view)
        compositeDisposable.clear()
    }
}
