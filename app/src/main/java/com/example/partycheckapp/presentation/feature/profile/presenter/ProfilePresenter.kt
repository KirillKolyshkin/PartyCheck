package com.example.partycheckapp.presentation.feature.profile.presenter

import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.bd.DBProvider
import com.example.partycheckapp.presentation.feature.profile.view.ProfileView
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class ProfilePresenter(private val dbProvider: DBProvider) : MvpPresenter<ProfileView>() {
    fun getUser() = dbProvider.getCurrentUser()
        .subscribeBy(onSuccess = {
            viewState.setUser(it)
        })

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getUser()
    }

    fun updateUSer(name: String, phone: String, cardNumber: Long?, bitmap: Bitmap?) =
        dbProvider.updateCurrentUser(name, phone, cardNumber, bitmap)
}