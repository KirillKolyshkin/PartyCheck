package com.example.partycheckapp.di.module

import com.example.numfac.di.scope.PartyScope
import com.example.partycheckapp.model.PartyModel
import com.example.partycheckapp.presenter.PartyListPresenter
import com.example.partycheckapp.view.fragments.bottom_nav_fragments.partyList.PartyListAdapter
import dagger.Module
import dagger.Provides

@Module
class PartyModule {

    @Provides
    @PartyScope
    fun providePartyListPresenter(model: PartyModel): PartyListPresenter =
        PartyListPresenter(model)


    @Provides
    @PartyScope
    fun providePartyModel(): PartyModel =
        PartyModel()

    @Provides
    @PartyScope
    fun providePartyListAdapter(): PartyListAdapter = PartyListAdapter()
}
