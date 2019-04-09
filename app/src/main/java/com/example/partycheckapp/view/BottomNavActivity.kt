package com.example.partycheckapp.view

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.partycheckapp.R
import com.example.partycheckapp.di.module.AppModule
import com.example.partycheckapp.di.module.PartyModule
import com.example.partycheckapp.view.fragments.bottom_nav_fragments.ProfileFragment
import com.example.partycheckapp.view.fragments.bottom_nav_fragments.partyList.PartyListFragment
import kotlinx.android.synthetic.main.activity_bottom_nav.*


class BottomNavActivity : AppCompatActivity() {

    private var menu: Menu? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_debtors -> {
                supportActionBar?.setTitle(R.string.debtors)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_search -> {
                supportActionBar?.setTitle(R.string.search)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_party_list -> {
                supportActionBar?.setTitle(R.string.my_party)
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, PartyListFragment.newInstance())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_profile -> {
                supportActionBar?.hide()
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

    companion object {
    }
}
