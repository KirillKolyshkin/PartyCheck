package com.example.partycheckapp.presentation.feature.party.addparty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.partycheckapp.R
import android.os.Bundle
import android.view.Menu
import android.view.View
import com.example.partycheckapp.presentation.feature.main.activity.MainActivity
import kotlinx.android.synthetic.main.main_activity_view.*

class AddPartyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_party)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp)
        supportActionBar?.setTitle(R.string.add_party)

        toolbar.setNavigationOnClickListener { onBackPressed() }

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, AddPartyFragment.newInstance())
                .commit()
        }

    }
}