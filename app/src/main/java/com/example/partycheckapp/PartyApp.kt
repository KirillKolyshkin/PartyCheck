package com.example.partycheckapp

import android.app.Application
import com.example.partycheckapp.presentation.di.component.AppComponent
import com.example.partycheckapp.presentation.di.component.DaggerAppComponent
import com.example.partycheckapp.presentation.di.module.AppModule

class PartyApp : Application() {

    private var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun getAppComponent(): AppComponent {
        if (appComponent == null) {
            appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
        }
        return appComponent as AppComponent
    }

    companion object {
        lateinit var instance: PartyApp
            private set
    }
}
