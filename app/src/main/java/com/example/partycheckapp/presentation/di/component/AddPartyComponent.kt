package com.example.partycheckapp.presentation.di.component

import com.example.numfac.di.scope.PartyScope
import com.example.partycheckapp.presentation.di.module.AddPartyModule
import com.example.partycheckapp.presentation.di.module.InteractorModule
import com.example.partycheckapp.presentation.feature.party.addparty.AddPartyFragment
import dagger.Subcomponent

@Subcomponent(modules = [AddPartyModule::class, InteractorModule::class])
@PartyScope
interface AddPartyComponent {

    fun inject(addPartyFragment: AddPartyFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): AddPartyComponent
    }
}