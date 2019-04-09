package com.example.numfac.di.component

import com.example.numfac.di.scope.PartyScope
import com.example.partycheckapp.model.PartyModel
import com.example.partycheckapp.view.fragments.bottom_nav_fragments.partyList.PartyListFragment
import dagger.Component
import dagger.Subcomponent

@Subcomponent(modules = [PartyModel::class])
@PartyScope
interface DateComponent {

    fun inject(partyListFragment: PartyListFragment)

    @Subcomponent.Builder
    interface Builder {

        fun build():DateComponent
    }
}
