package com.example.partycheckapp.presentation.di.component

import com.example.numfac.di.scope.PartyScope
import com.example.partycheckapp.presentation.di.module.InteractorModule
import com.example.partycheckapp.presentation.di.module.PartyDetailsModule
import com.example.partycheckapp.presentation.feature.partydetails.addpurchase.AddPurchaseFragment
import com.example.partycheckapp.presentation.feature.partydetails.mainpartyscreen.MainPartyScreenFragment
import com.example.partycheckapp.presentation.feature.partydetails.purchasedetails.PurchaseDetailsFragment
import com.example.partycheckapp.presentation.feature.partydetails.purchaselist.PurchaseListFragment
import dagger.Subcomponent

@Subcomponent(modules = [PartyDetailsModule::class, InteractorModule::class])
@PartyScope
interface PartyDetailsComponent {

    fun inject(mainPartyScreenFragment: MainPartyScreenFragment)
    fun inject(purchaseListFragment: PurchaseListFragment)
    fun inject(addPurchaseFragment: AddPurchaseFragment)
    fun inject(purchaseDetailsFragment: PurchaseDetailsFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): PartyDetailsComponent
    }
}
