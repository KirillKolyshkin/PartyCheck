package com.example.partycheckapp.presentation.di.module

import android.content.SharedPreferences
import com.example.partycheckapp.domain.bd.DBProvider
import com.example.partycheckapp.domain.debtor.DebtorMapper
import com.example.partycheckapp.domain.user.UserInteractor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    fun provideUserModule(firebaseAuth: FirebaseAuth, sharedPreferences: SharedPreferences) =
        UserInteractor(firebaseAuth, sharedPreferences)

    @Provides
    fun provideBDProvider(
        firebaseFirestore: FirebaseFirestore,
        sharedPreferences: SharedPreferences,
        firebaseStorage: FirebaseStorage
    ) = DBProvider(firebaseFirestore, sharedPreferences, firebaseStorage)

    @Provides
    fun provideDebtorMapper() = DebtorMapper()
}
