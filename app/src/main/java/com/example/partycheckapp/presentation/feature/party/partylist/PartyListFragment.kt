package com.example.partycheckapp.presentation.feature.party.partylist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.partycheckapp.PartyApp
import com.example.partycheckapp.R
import com.example.partycheckapp.data.party.Party
import com.example.partycheckapp.presentation.feature.party.addparty.AddPartyActivity
import com.example.partycheckapp.presentation.feature.partydetails.PartyDetailsActivity
import kotlinx.android.synthetic.main.fragmet_my_party_list.*
import javax.inject.Inject

class PartyListFragment : MvpAppCompatFragment(),
    PartyListView,
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    @InjectPresenter
    lateinit var partyListPresenter: PartyListPresenter

    @ProvidePresenter
    fun initPresenter() = partyListPresenter

    private val partyListAdapter = PartyListAdapter { onItemClick(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        PartyApp.instance
            .getAppComponent()
            .mainActivityComponent()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragmet_my_party_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                this.context, resources.configuration.orientation
            )
        )
        val manager = LinearLayoutManager(context)
        recycler_view.adapter = partyListAdapter
        recycler_view.layoutManager = manager
        partyListPresenter.setMyPartyList()
        initClickListeners()
        swipe_container.setColorSchemeResources(R.color.colorAccent)
        swipe_container.setOnRefreshListener(this)
    }

    private fun initClickListeners() {
        btn_add.setOnClickListener {
            startAddPartyActivity()
        }
    }

    private fun onItemClick(party: Party) {
        val intent = Intent(activity, PartyDetailsActivity::class.java)
        intent.putExtra("party_id", party.ref)
        startActivity(intent)
    }

    override fun onRefresh() {
        partyListPresenter.setMyPartyList()
    }

    private fun startAddPartyActivity() {
        startActivity(Intent(context, AddPartyActivity::class.java))
    }

    override fun showPartyList(dataList: ArrayList<Party>) {
        partyListAdapter.list = dataList
        partyListAdapter.notifyDataSetChanged()
        swipe_container.isRefreshing = false
    }

    companion object {
        fun newInstance(): PartyListFragment =
            PartyListFragment()
    }
}
