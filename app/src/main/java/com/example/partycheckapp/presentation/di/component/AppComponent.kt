package com.example.partycheckapp.presentation.di.component

import com.example.partycheckapp.presentation.di.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun mainActivityComponent(): MainActivityComponent.Builder
    fun partyDetailsComponent(): PartyDetailsComponent.Builder
    fun signInComponent(): SignInComponent.Builder
    fun addPartyComponent(): AddPartyComponent.Builder
}
