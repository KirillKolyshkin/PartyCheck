package com.example.partycheckapp.presentation.feature.partydetails.purchaselist

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
import com.example.partycheckapp.data.party.Purchase
import com.example.partycheckapp.presentation.feature.partydetails.PartyDetailsActivity
import com.example.partycheckapp.presentation.feature.partydetails.addpurchase.AddPurchaseFragment
import com.example.partycheckapp.presentation.feature.partydetails.mainpartyscreen.MainPartyScreenFragment
import com.example.partycheckapp.presentation.feature.partydetails.purchasedetails.PurchaseDetailsFragment
import kotlinx.android.synthetic.main.fragment_purchase_list.*
import javax.inject.Inject

class PurchaseListFragment : MvpAppCompatFragment(),
    PurchaseListView,
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    @InjectPresenter
    lateinit var purchaseListPresenter: PurchaseListPresenter

    @ProvidePresenter
    fun initPresenter() = purchaseListPresenter

    private val purchaseListAdapter = PurchaseListAdapter { onItemClick(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        PartyApp.instance
            .getAppComponent()
            .partyDetailsComponent()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_purchase_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                this.context, resources.configuration.orientation
            )
        )
        val manager = LinearLayoutManager(context)
        recycler_view.adapter = purchaseListAdapter
        recycler_view.layoutManager = manager
        arguments?.getString("party_id")?.let { purchaseListPresenter.showPurchase(it) }
        initClickListeners()
        swipe_container.setColorSchemeResources(R.color.colorAccent)
        swipe_container.setOnRefreshListener(this)
        initToolbar()
    }

    private fun initToolbar() {
        val activity = (activity as PartyDetailsActivity)
        activity.setSupportActionBar(toolbar)
        toolbar.title = "Purchase List"
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp)
        toolbar.setNavigationOnClickListener {
            var purchaseId = arguments?.getString("party_id") ?: ""
            fragmentManager?.let {
                it.beginTransaction()
                    .replace(R.id.container, MainPartyScreenFragment.newInstance(purchaseId))
                    .commit()
            }

        }
    }

    private fun initClickListeners() {
        var purchaseId = arguments?.getString("party_id") ?: ""
        btn_add.setOnClickListener {
            fragmentManager?.let {
                it.beginTransaction()
                    .replace(R.id.container, AddPurchaseFragment.newInstance(purchaseId))
                    .commit()
            }
        }
    }

    private fun onItemClick(purchase: Purchase) {
        arguments?.getString("party_id")?.let {partyId ->
            fragmentManager?.let {
                it.beginTransaction()
                    .replace(R.id.container, PurchaseDetailsFragment.newInstance(partyId, purchase.title))
                    .commit()
            }
        }
    }

    override fun onRefresh() {
        arguments?.getString("party_id")?.let { purchaseListPresenter.showPurchase(it) }
    }

    override fun showPurchaseList(dataList: ArrayList<Purchase>) {
        purchaseListAdapter.list = dataList
        purchaseListAdapter.notifyDataSetChanged()
        swipe_container.isRefreshing = false
    }

    companion object {
        fun newInstance(partyId: String): PurchaseListFragment {
            val args = Bundle()
            args.putString("party_id", partyId)
            val fragment = PurchaseListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
