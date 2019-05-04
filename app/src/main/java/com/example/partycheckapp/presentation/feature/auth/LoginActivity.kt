package com.example.partycheckapp.presentation.feature.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.partycheckapp.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, SignInFragment.newInstance())
                .commit()
        }

    }
}
