package com.example.partycheckapp.presentation.feature.auth.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.partycheckapp.R
import com.example.partycheckapp.presentation.feature.auth.fragment.LoginFragment

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commit()
        }

    }
}
