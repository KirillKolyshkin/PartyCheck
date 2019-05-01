package com.example.partycheckapp.presentation.feature.party.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.partycheckapp.R
import com.example.partycheckapp.data.debtors.UserDebtor
import com.example.partycheckapp.presentation.feature.debtors.adapter.DebtorsListAdapter
import com.example.partycheckapp.presentation.feature.debtors.presenter.DebtorsListPresenter
import kotlinx.android.synthetic.main.user_list.*
import androidx.recyclerview.widget.DividerItemDecoration
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.partycheckapp.PartyApp
import javax.inject.Inject


class DebtorsListFragment : MvpAppCompatFragment(), DebtorsListView {

    @Inject
    @InjectPresenter
    lateinit var debtorsListPresenter: DebtorsListPresenter

    @ProvidePresenter
    fun initPresenter() = debtorsListPresenter


    private val recyclerAdapter = DebtorsListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        PartyApp
            .instance
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
        recycler_view.addItemDecoration(DividerItemDecoration(
            this.context, resources.configuration.orientation
        ))
        val manager = LinearLayoutManager(context)
        recycler_view.adapter = recyclerAdapter
        recycler_view.layoutManager = manager
        debtorsListPresenter.setUsersDebtorsList()
    }

    override fun showDebtorsList(dataList: ArrayList<UserDebtor>) {
        recyclerAdapter.list = dataList
    }

    companion object {
        fun newInstance(): DebtorsListFragment {
            val args = Bundle()
            val fragment = DebtorsListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
