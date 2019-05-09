package com.example.partycheckapp.presentation.feature.party.addparty

import androidx.appcompat.app.AppCompatActivity
import com.example.partycheckapp.R
import android.os.Bundle

class AddPartyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_party)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, AddPartyFragment.newInstance())
                .commit()
        }

    }
}