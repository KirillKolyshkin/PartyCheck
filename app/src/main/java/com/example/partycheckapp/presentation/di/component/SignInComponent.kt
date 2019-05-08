package com.example.partycheckapp.presentation.di.component

import com.example.numfac.di.scope.PartyScope
import com.example.partycheckapp.presentation.di.module.InteractorModule
import com.example.partycheckapp.presentation.di.module.SignInModule
import com.example.partycheckapp.presentation.feature.auth.SignInFragment
import dagger.Subcomponent

@Subcomponent(modules = [SignInModule::class, InteractorModule::class])
@PartyScope
interface SignInComponent {

    fun inject(signInFragment: SignInFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): SignInComponent
    }
}