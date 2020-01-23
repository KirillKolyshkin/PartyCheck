package com.example.partycheckapp.presentation.feature.searchparty

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.partycheckapp.PartyApp
import com.example.partycheckapp.R
import com.example.partycheckapp.data.party.Party
import kotlinx.android.synthetic.main.fragment_party_list.*
import javax.inject.Inject
import android.content.DialogInterface
import android.content.Intent
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import com.example.partycheckapp.presentation.feature.main.activity.MainActivity
import com.example.partycheckapp.presentation.feature.partydetails.PartyDetailsActivity

class SearchPartyListFragment : MvpAppCompatFragment(),
    SearchPartyListView,
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    @InjectPresenter
    lateinit var searchPartyListPresenter: SearchPartyListPresenter

    @ProvidePresenter
    fun initPresenter() = searchPartyListPresenter

    private val searchPartyListAdapter = PartySearchListAdapter { onItemClick(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        PartyApp.instance
            .getAppComponent()
            .mainActivityComponent()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_party_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                this.context, resources.configuration.orientation
            )
        )
        searchPartyListPresenter.getUserPartyList()
        recycler_view.adapter = searchPartyListAdapter
        recycler_view.layoutManager = LinearLayoutManager(context)
        searchPartyListPresenter.setPartySearchList()
        swipe_container.setColorSchemeResources(R.color.colorAccent)
        swipe_container.setOnRefreshListener(this)
        initToolbar()
    }

    private fun initToolbar() {
        val activity = (activity as MainActivity)
        activity.setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.search_party)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        val activity = (activity as MainActivity)
        activity.menuInflater.inflate(R.menu.toolbar_items, menu)
        val mSearch = menu?.findItem(R.id.action_search)
        val mSearchView = mSearch?.actionView as SearchView
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                getFilterList(newText)
                return false
            }
        })
        localMenu = menu
    }

    fun getFilterList(textQuery: String) =
        showPartyList(ArrayList(dataList.filter { it.title.contains(textQuery) }))

    override fun onRefresh() {
        searchPartyListPresenter.setPartySearchList()
    }

    private fun onItemClick(party: Party) {
        var flag = true
        for (_party in dataList)
            if (_party.ref == party.ref)
                flag = false
        if (flag)
            showDialog(party)
        else {
            val intent = Intent(activity, PartyDetailsActivity::class.java)
            intent.putExtra("party_id", party.ref)
            startActivity(intent)
        }
    }

    private fun showDialog(party: Party) {
        val alert = context?.let { AlertDialog.Builder(it) }
        if (alert != null) {
            alert.setTitle("Password Confirm")

            alert.setMessage("Enter Party Passowrd")

            val input = EditText(context)
            alert.setView(input)

            alert.setPositiveButton("Ok", DialogInterface.OnClickListener { _, whichButton ->
                val partyPassword = input.text.toString()
                if (partyPassword == party.password) {
                    party.ref?.let { searchPartyListPresenter.addUserToParty(it) }
                    val intent = Intent(activity, PartyDetailsActivity::class.java)
                    intent.putExtra("party_id", party.ref)
                    startActivity(intent)
                }
            })

            alert.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, whichButton ->
                dialog.dismiss()
            })

            alert.show()
        }
    }

    override fun getUserPartyList(parties: ArrayList<Party>) {
        dataList = parties
    }

    override fun showPartyList(dataList: ArrayList<Party>) {
        searchPartyListAdapter.list = dataList
        searchPartyListAdapter.notifyDataSetChanged()
        swipe_container.isRefreshing = false
    }

    companion object {
        var localMenu: Menu? = null
        var dataList = ArrayList<Party>()
        fun newInstance(): SearchPartyListFragment =
            SearchPartyListFragment()
    }
}
