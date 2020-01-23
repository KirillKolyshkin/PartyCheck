package com.example.partycheckapp.presentation.feature.main.activity

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.partycheckapp.R
import com.example.partycheckapp.presentation.feature.party.view.DebtorsListFragment
import com.example.partycheckapp.presentation.feature.profile.ProfileFragment
import com.example.partycheckapp.presentation.feature.party.partylist.PartyListFragment
import com.example.partycheckapp.presentation.feature.searchparty.SearchPartyListFragment
import kotlinx.android.synthetic.main.main_activity_view.*

class MainActivity : AppCompatActivity() {

    private var menu: Menu? = null

    private val profileFragment = ProfileFragment.newInstance()
    private val debtorsListFragment = DebtorsListFragment.newInstance()
    private val searchPartyListFragment = SearchPartyListFragment.newInstance()
    private val partyListFragment = PartyListFragment.newInstance()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_debtors -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, debtorsListFragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_search -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, searchPartyListFragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_party_list -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, partyListFragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_profile -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, profileFragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_view)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.action_party_list
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, partyListFragment)
                .commit()
        }
    }

}
