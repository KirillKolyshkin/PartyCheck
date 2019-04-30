package com.example.partycheckapp.presentation.di.module

import android.content.SharedPreferences
import com.example.partycheckapp.domain.bd.DBProvider
import com.example.partycheckapp.domain.user.UserInteractor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    fun provideUserModule(fb: FirebaseAuth, sp: SharedPreferences) = UserInteractor(fb, sp)

    @Provides
    fun provideUserProvider(df: FirebaseFirestore, sp: SharedPreferences, fs: FirebaseStorage) = DBProvider(df, sp, fs)
}