package com.example.partycheckapp.presentation.di.module

import com.example.partycheckapp.domain.bd.DBProvider
import com.example.partycheckapp.domain.user.UserInteractor
import com.example.partycheckapp.presentation.feature.auth.SignInPresenter
import dagger.Module
import dagger.Provides

@Module
class SignInModule {
    @Provides
    fun provideSignInPresenter(userInteractor: UserInteractor, userProvider: DBProvider) =
        SignInPresenter(userInteractor, userProvider)
}
