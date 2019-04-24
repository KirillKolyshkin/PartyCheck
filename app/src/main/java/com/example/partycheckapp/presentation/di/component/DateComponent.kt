package com.example.partycheckapp.presentation.di.component

import com.example.numfac.di.scope.PartyScope
import com.example.partycheckapp.presentation.di.module.InteractorModule
import com.example.partycheckapp.presentation.di.module.PartyModule
import com.example.partycheckapp.presentation.di.module.PresenterModule
import com.example.partycheckapp.presentation.feature.auth.view.SignInFragment
import com.example.partycheckapp.presentation.feature.party.view.DebtorsListFragment
import com.example.partycheckapp.presentation.feature.party.view.PartyListFragment
import com.example.partycheckapp.presentation.feature.profile.view.ProfileFragment
import dagger.Subcomponent

@Subcomponent(modules = [PartyModule::class, PresenterModule::class, InteractorModule::class])
@PartyScope
interface DateComponent {

    fun inject(partyListFragment: PartyListFragment)
    fun inject(debtorsListFragment: DebtorsListFragment)
    fun inject(signInFragment: SignInFragment)
    fun inject(profileFragment: ProfileFragment)

    @Subcomponent.Builder
    interface Builder {

        fun build(): DateComponent
    }
}
