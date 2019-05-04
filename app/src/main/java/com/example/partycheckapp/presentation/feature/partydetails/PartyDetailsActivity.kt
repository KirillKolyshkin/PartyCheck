package com.example.partycheckapp.presentation.feature.partydetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.partycheckapp.R
import com.example.partycheckapp.data.party.Party
import com.example.partycheckapp.data.party.PartyWithDebt
import com.example.partycheckapp.presentation.feature.partydetails.mainpartyscreen.MainPartyScreenFragment
import com.google.firebase.firestore.auth.User

class PartyDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_party_details)
        val partyId = intent.extras.getString("party_id")
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, MainPartyScreenFragment.newInstance(partyId))
                .commit()
        }

    }
}
