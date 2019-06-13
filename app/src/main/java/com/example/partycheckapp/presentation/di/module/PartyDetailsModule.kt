package com.example.partycheckapp.presentation.di.module

import com.example.partycheckapp.domain.bd.DBProvider
import com.example.partycheckapp.presentation.feature.partydetails.addpurchase.AddPurchasePresenter
import com.example.partycheckapp.presentation.feature.partydetails.mainpartyscreen.MainPartyScreenPresenter
import com.example.partycheckapp.presentation.feature.partydetails.purchasedetails.PurchaseDetailsPresenter
import com.example.partycheckapp.presentation.feature.partydetails.purchaselist.PurchaseListPresenter
import dagger.Module
import dagger.Provides

@Module
class PartyDetailsModule {
    @Provides
    fun provideMainPartyScreenPresenter(userProvider: DBProvider) = MainPartyScreenPresenter(userProvider)

    @Provides
    fun providePurchaseListPresenter(userProvider: DBProvider) = PurchaseListPresenter(userProvider)

    @Provides
    fun provideAddPurchasePresenter(userProvider: DBProvider) = AddPurchasePresenter(userProvider)

    @Provides
    fun providePurchaseDetailsPresenter(userProvider: DBProvider) = PurchaseDetailsPresenter(userProvider)
}
