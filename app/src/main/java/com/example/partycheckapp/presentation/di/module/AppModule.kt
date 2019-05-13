package com.example.partycheckapp.presentation.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = app.baseContext

    @Provides
    @Singleton
    fun providePreferences(context: Context): SharedPreferences = context.getSharedPreferences(PREFS_KEY, 0)

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideDatabaseReference(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    companion object {
        private const val PREFS_KEY = "com.example.partycheckapp"
    }
}

