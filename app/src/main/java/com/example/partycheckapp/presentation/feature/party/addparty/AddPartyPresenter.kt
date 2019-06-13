package com.example.partycheckapp.presentation.feature.party.addparty

import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.partycheckapp.domain.bd.DBProvider
import java.util.*

@InjectViewState
class AddPartyPresenter(private val dbProvider: DBProvider) : MvpPresenter<AddPartyView>() {

    fun addParty(
        title: String,
        description: String?,
        location: String,
        date: Date,
        password: String,
        bitmap: Bitmap?
    ) = dbProvider.addNewParty(title, description, location, date, password, bitmap)
}
