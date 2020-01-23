package com.example.partycheckapp.presentation.di.component

import com.example.numfac.di.scope.PartyScope
import com.example.partycheckapp.presentation.di.module.InteractorModule
import com.example.partycheckapp.presentation.di.module.MainActivityModule
import com.example.partycheckapp.presentation.feature.debtordetails.DebtorDetailsFragment
import com.example.partycheckapp.presentation.feature.party.view.DebtorsListFragment
import com.example.partycheckapp.presentation.feature.party.partylist.PartyListFragment
import com.example.partycheckapp.presentation.feature.profile.ProfileFragment
import com.example.partycheckapp.presentation.feature.searchparty.SearchPartyListFragment
import dagger.Subcomponent

@Subcomponent(modules = [MainActivityModule::class, InteractorModule::class])
@PartyScope
interface MainActivityComponent {

    fun inject(partyListFragment: PartyListFragment)
    fun inject(debtorsListFragment: DebtorsListFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(searchPartyListFragment: SearchPartyListFragment)
    fun inject(debtorDetailsFragment: DebtorDetailsFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): MainActivityComponent
    }
}
