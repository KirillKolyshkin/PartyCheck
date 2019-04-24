package com.example.partycheckapp.presentation.feature.party.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.partycheckapp.R
import com.example.partycheckapp.data.party.PartyWithDebt
import com.example.partycheckapp.presentation.feature.party.adapter.PartyListAdapter
import com.example.partycheckapp.presentation.feature.party.presenter.PartyListPresenter
import kotlinx.android.synthetic.main.party_list.*

class PartyListFragment : MvpAppCompatFragment(), PartyListView {

    @InjectPresenter
    lateinit var partyListPresenter: PartyListPresenter

    private val recyclerAdapter = PartyListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.party_list, container, false)

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
        partyListPresenter.setPartyDebtList()
    }

    override fun showPartyList(dataList: ArrayList<PartyWithDebt>) {
        recyclerAdapter.list = dataList
    }

    companion object {
        fun newInstance(): PartyListFragment {
            val args = Bundle()
            val fragment = PartyListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}