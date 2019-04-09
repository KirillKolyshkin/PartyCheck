package com.example.numfac.di.component

import android.content.Context
import com.example.partycheckapp.di.module.AppModule
import com.example.partycheckapp.di.module.PartyModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class]
)
interface AppComponent {

    fun dateComponent(): DateComponent
}
