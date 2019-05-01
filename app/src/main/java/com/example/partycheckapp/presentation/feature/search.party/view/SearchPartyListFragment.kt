package com.example.partycheckapp.presentation.feature.searchParty.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.partycheckapp.R
import com.example.partycheckapp.data.party.Party
import com.example.partycheckapp.presentation.feature.searchParty.adapter.PartySearchListAdapter
import com.example.partycheckapp.presentation.feature.searchParty.presenter.PartyListPresenter
import kotlinx.android.synthetic.main.fragment_party_list.*

class SearchPartyListFragment : MvpAppCompatFragment(),
    SearchPartyListView {

    @InjectPresenter
    lateinit var partyListPresenter: PartyListPresenter

    private val recyclerAdapter = PartySearchListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
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
        recycler_view.adapter = recyclerAdapter
        recycler_view.layoutManager = manager
        partyListPresenter.setPartySearchList()
    }

    override fun showPartyList(dataList: ArrayList<Party>) {
        recyclerAdapter.list = dataList
    }

    companion object {
        fun newInstance(): SearchPartyListFragment {
            val args = Bundle()
            val fragment = SearchPartyListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
