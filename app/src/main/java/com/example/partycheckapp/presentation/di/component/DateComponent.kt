package com.example.partycheckapp.presentation.di.component

import com.example.numfac.di.scope.PartyScope
import com.example.partycheckapp.presentation.di.module.InteractorModule
import com.example.partycheckapp.presentation.di.module.PartyModule
import com.example.partycheckapp.presentation.di.module.PresenterModule
import com.example.partycheckapp.presentation.feature.auth.SignInFragment
import com.example.partycheckapp.presentation.feature.party.addparty.AddPartyFragment
import com.example.partycheckapp.presentation.feature.party.view.DebtorsListFragment
import com.example.partycheckapp.presentation.feature.party.partylist.PartyListFragment
import com.example.partycheckapp.presentation.feature.partydetails.addpurchase.AddPurchaseFragment
import com.example.partycheckapp.presentation.feature.partydetails.mainpartyscreen.MainPartyScreenFragment
import com.example.partycheckapp.presentation.feature.partydetails.purchaselist.PurchaseListFragment
import com.example.partycheckapp.presentation.feature.profile.ProfileFragment
import com.example.partycheckapp.presentation.feature.search.party.SearchPartyListFragment
import dagger.Subcomponent

@Subcomponent(modules = [PartyModule::class, PresenterModule::class, InteractorModule::class])
@PartyScope
interface DateComponent {

    fun inject(partyListFragment: PartyListFragment)
    fun inject(debtorsListFragment: DebtorsListFragment)
    fun inject(signInFragment: SignInFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(addPartyFragment: AddPartyFragment)
    fun inject(searchPartyListFragment: SearchPartyListFragment)
    fun inject(mainPartyScreenFragment: MainPartyScreenFragment)
    fun inject(purchaseListFragment: PurchaseListFragment)
    fun inject(addPurchaseFragment: AddPurchaseFragment)

    @Subcomponent.Builder
    interface Builder {

        fun build(): DateComponent
    }
}
