package com.example.partycheckapp.presentation.feature.search.party

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
import kotlinx.android.synthetic.main.fragment_party_list.*
import javax.inject.Inject

class SearchPartyListFragment : MvpAppCompatFragment(),
    SearchPartyListView,
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    @InjectPresenter
    lateinit var searchPartyListPresenter: SearchPartyListPresenter

    @ProvidePresenter
    fun initPresenter() = searchPartyListPresenter

    private val searchPartyListAdapter = PartySearchListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        PartyApp.instance
            .getAppComponent()
            .dateComponent()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_party_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                this.context, resources.configuration.orientation
            )
        )
        val manager = LinearLayoutManager(context)
        recycler_view.adapter = searchPartyListAdapter
        recycler_view.layoutManager = manager
        searchPartyListPresenter.setPartySearchList()
        swipe_container.setColorSchemeResources(R.color.colorAccent)
        swipe_container.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        searchPartyListPresenter.setPartySearchList()
    }

    override fun showPartyList(dataList: ArrayList<Party>) {
        searchPartyListAdapter.list = dataList
        searchPartyListAdapter.notifyDataSetChanged()
        swipe_container.isRefreshing = false
    }

    companion object {
        fun newInstance(): SearchPartyListFragment =
            SearchPartyListFragment()
    }
}
