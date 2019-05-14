package com.example.partycheckapp.presentation.di.module

import com.example.partycheckapp.domain.bd.DBProvider
import com.example.partycheckapp.domain.debtor.DebtorMapper
import com.example.partycheckapp.domain.user.UserInteractor
import com.example.partycheckapp.presentation.feature.auth.SignInPresenter
import com.example.partycheckapp.presentation.feature.debtorDetails.DebtorDetailsPresenter
import com.example.partycheckapp.presentation.feature.debtors.DebtorsListPresenter
import com.example.partycheckapp.presentation.feature.party.addparty.AddPartyPresenter
import com.example.partycheckapp.presentation.feature.party.partylist.PartyListPresenter
import com.example.partycheckapp.presentation.feature.partydetails.addpurchase.AddPurchasePresenter
import com.example.partycheckapp.presentation.feature.partydetails.mainpartyscreen.MainPartyScreenPresenter
import com.example.partycheckapp.presentation.feature.partydetails.purchasedetails.PurchaseDetailsPresenter
import com.example.partycheckapp.presentation.feature.partydetails.purchaselist.PurchaseListPresenter
import com.example.partycheckapp.presentation.feature.profile.ProfilePresenter
import com.example.partycheckapp.presentation.feature.search.party.SearchPartyListPresenter
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideDebtorsListPresenter(userProvider: DBProvider, debtorMapper: DebtorMapper) = DebtorsListPresenter(userProvider, debtorMapper)

    @Provides
    fun provideProfilePresenter(userProvider: DBProvider) = ProfilePresenter(userProvider)

    @Provides
    fun providePartyListPresenter(userProvider: DBProvider) = PartyListPresenter(userProvider)

    @Provides
    fun provideSearchPartyListPresenter(userProvider: DBProvider) = SearchPartyListPresenter(userProvider)

    @Provides
    fun provideDebtorDetailsPresenter(userProvider: DBProvider) = DebtorDetailsPresenter(userProvider)
}
