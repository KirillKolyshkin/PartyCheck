package com.example.partycheckapp.presentation.di.module

import com.example.partycheckapp.domain.bd.DBProvider
import com.example.partycheckapp.domain.user.UserInteractor
import com.example.partycheckapp.presentation.feature.auth.SignInPresenter
import com.example.partycheckapp.presentation.feature.debtors.presenter.DebtorsListPresenter
import com.example.partycheckapp.presentation.feature.party.addparty.AddPartyPresenter
import com.example.partycheckapp.presentation.feature.party.partylist.PartyListPresenter
import com.example.partycheckapp.presentation.feature.partydetails.addpurchase.AddPurchasePresenter
import com.example.partycheckapp.presentation.feature.partydetails.mainpartyscreen.MainPartyScreenPresenter
import com.example.partycheckapp.presentation.feature.partydetails.purchaselist.PurchaseListPresenter
import com.example.partycheckapp.presentation.feature.profile.ProfilePresenter
import com.example.partycheckapp.presentation.feature.search.party.SearchPartyListPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {
    @Provides
    fun provideSignInPresenter(userInteractor: UserInteractor, userProvider: DBProvider)=
        SignInPresenter(userInteractor, userProvider)

    @Provides
    fun provideDebtorsListPresenter(userInteractor: UserInteractor) = DebtorsListPresenter(userInteractor)

    @Provides
    fun provideProfilePresenter(userProvider: DBProvider) =
        ProfilePresenter(userProvider)

    @Provides
    fun provideAddPartyPresenter(userProvider: DBProvider) = AddPartyPresenter(userProvider)

    @Provides
    fun providePartyListPresenter(userProvider: DBProvider) = PartyListPresenter(userProvider)

    @Provides
    fun provideSearchPartyListPresenter(userProvider: DBProvider) = SearchPartyListPresenter(userProvider)

    @Provides
    fun provideMainPartyScreenPresenter(userProvider: DBProvider) = MainPartyScreenPresenter(userProvider)

    @Provides
    fun providePurchaseListPresenter(userProvider: DBProvider) = PurchaseListPresenter(userProvider)

    @Provides
    fun provideAddPurchasePresenter(userProvider: DBProvider) = AddPurchasePresenter(userProvider)
}
