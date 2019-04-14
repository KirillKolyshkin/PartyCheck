package com.example.partycheckapp.presentation.feature.party.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.partycheckapp.R
import com.example.partycheckapp.data.party.Party
import com.example.partycheckapp.presentation.feature.party.adapter.PartyListAdapter
import com.example.partycheckapp.presentation.feature.party.presenter.PartyListPresenter
import kotlinx.android.synthetic.main.user_list.*

class PartyListFragment : MvpAppCompatFragment(), PartyListView {

    @InjectPresenter
    lateinit var partyListPresenter: PartyListPresenter

    private val recyclerAdapter = PartyListAdapter()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.party_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = LinearLayoutManager(context)
        recycler_view.adapter = recyclerAdapter
        recycler_view.layoutManager = manager
        partyListPresenter.setPartyList()
    }

    override fun showPartyList(dataList: ArrayList<Party>) {
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
