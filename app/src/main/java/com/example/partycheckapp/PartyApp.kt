package com.example.partycheckapp

import android.app.Application
import com.example.partycheckapp.presentation.di.component.AppComponent
import com.example.partycheckapp.presentation.di.component.DaggerAppComponent

class PartyApp : Application() {

    private var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()

        instance = this
    }

    fun getAppComponent(): AppComponent {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder().build()
        }
        return appComponent as AppComponent
    }

    companion object {
        @JvmStatic lateinit var instance: PartyApp
            private set
    }
}
