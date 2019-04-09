package com.example.partycheckapp.view.fragments.bottom_nav_fragments.partyList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.partycheckapp.R
import com.example.partycheckapp.di.module.PartyModule
import com.example.partycheckapp.entity.Party
import com.example.partycheckapp.presenter.PartyListPresenter
import com.example.partycheckapp.view.BottomNavActivity
import kotlinx.android.synthetic.main.user_list.*


class PartyListFragment : MvpAppCompatFragment(), PartyListView {

    var recyclerAdapter = PartyListAdapter()

    @InjectPresenter
    lateinit var partyListPresenter: PartyListPresenter

    @ProvidePresenter
    fun initPresenter() = partyListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
