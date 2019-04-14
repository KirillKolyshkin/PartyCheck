package com.example.partycheckapp.presentation.feature.main.activity

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.partycheckapp.R
import com.example.partycheckapp.presentation.feature.party.view.DebtorsListFragment
import com.example.partycheckapp.presentation.feature.profile.view.ProfileFragment
import com.example.partycheckapp.presentation.feature.party.view.PartyListFragment
import kotlinx.android.synthetic.main.main_activity_view.*


class MainActivity : AppCompatActivity() {

    private var menu: Menu? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_debtors -> {
                supportActionBar?.show()
                supportActionBar?.setTitle(R.string.debtors)
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, DebtorsListFragment.newInstance())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_search -> {
                supportActionBar?.show()
                supportActionBar?.setTitle(R.string.search)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_party_list -> {
                supportActionBar?.show()
                supportActionBar?.setTitle(R.string.my_party)
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, PartyListFragment.newInstance())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_profile -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, ProfileFragment.newInstance())
                    .commit()
                supportActionBar?.hide()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_view)
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
