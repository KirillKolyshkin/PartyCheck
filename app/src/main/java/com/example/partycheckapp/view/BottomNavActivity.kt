package com.example.partycheckapp.view

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.partycheckapp.R
import com.example.partycheckapp.view.fragments.bottom_nav_fragments.ProfileFragment
import com.example.partycheckapp.view.fragments.login_fragments.LoginFragment
import kotlinx.android.synthetic.main.activity_bottom_nav.*


class BottomNavActivity : AppCompatActivity() {

    private var menu: Menu? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_debtors -> {
                message.setText(R.string.debtors)
                supportActionBar?.setTitle(R.string.debtors)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_search -> {
                message.setText(R.string.search)
                supportActionBar?.setTitle(R.string.search)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_party_list -> {
                message.setText(R.string.my_party)
                supportActionBar?.setTitle(R.string.my_party)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_profile -> {
                supportActionBar?.hide()//.setTitle(R.string.profile)
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, ProfileFragment.newInstance())
                    .commit()

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_items, menu)
        this.menu = menu
        return true
    }
}
