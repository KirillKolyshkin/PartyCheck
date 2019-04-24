package com.example.partycheckapp.presentation.feature.profile.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.bd.DBProvider
import com.example.partycheckapp.presentation.feature.profile.view.ProfileView
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class ProfilePresenter(private val dbProvider: DBProvider) : MvpPresenter<ProfileView>() {
    fun getUser() = dbProvider.getCurrentUser()
        .subscribeBy ( onSuccess = {
            viewState.setUser(it)
        } )

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getUser()
    }
    //viewState.setUser(dbProvider.getCurrentUser())
}