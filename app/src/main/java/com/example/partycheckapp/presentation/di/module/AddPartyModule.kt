package com.example.partycheckapp.presentation.di.module

import com.example.partycheckapp.domain.bd.DBProvider
import com.example.partycheckapp.presentation.feature.party.addparty.AddPartyPresenter
import dagger.Module
import dagger.Provides

@Module
class AddPartyModule {
    @Provides
    fun provideAddPartyPresenter(userProvider: DBProvider) = AddPartyPresenter(userProvider)
}
