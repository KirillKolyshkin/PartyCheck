package com.example.partycheckapp.presentation.di.component

import com.example.numfac.di.scope.PartyScope
import com.example.partycheckapp.presentation.di.module.PartyModule
import com.example.partycheckapp.presentation.feature.party.view.DebtorsListFragment
import com.example.partycheckapp.presentation.feature.party.view.PartyListFragment
import dagger.Subcomponent

@Subcomponent(modules = [PartyModule::class])
@PartyScope
interface DateComponent {

    fun inject(partyListFragment: PartyListFragment)
    fun inject(debtorsListFragment: DebtorsListFragment)

    @Subcomponent.Builder
    interface Builder {

        fun build(): DateComponent
    }
}
