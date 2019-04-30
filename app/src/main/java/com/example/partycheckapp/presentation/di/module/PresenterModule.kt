package com.example.partycheckapp.presentation.di.module

import com.example.partycheckapp.domain.bd.DBProvider
import com.example.partycheckapp.domain.user.UserInteractor
import com.example.partycheckapp.presentation.feature.auth.presenter.SignInPresenter
import com.example.partycheckapp.presentation.feature.debtors.presenter.DebtorsListPresenter
import com.example.partycheckapp.presentation.feature.party.addparty.AddPartyPresenter
import com.example.partycheckapp.presentation.feature.profile.presenter.ProfilePresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {
    @Provides
    fun provideSignInPresenter(userInteractor: UserInteractor, userProvider: DBProvider)= SignInPresenter(userInteractor, userProvider)

    @Provides
    fun provideDebtorsListPresenter(userInteractor: UserInteractor) = DebtorsListPresenter(userInteractor)

    @Provides
    fun provideProfilePresenter(userProvider: DBProvider) = ProfilePresenter(userProvider)

    @Provides
    fun provideAddPartyPresenter(userProvider: DBProvider) = AddPartyPresenter(userProvider)

}
