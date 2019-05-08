package com.example.partycheckapp.presentation.feature.debtorDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.partycheckapp.R

class DebtorDetailsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_party_details)
        val debtId = intent.extras.getString("debt_ref")
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, DebtorDetailsFragment.newInstance(debtId))
                .commit()
        }

    }
}
