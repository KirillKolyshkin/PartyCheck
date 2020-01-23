package com.example.partycheckapp.presentation.feature.partydetails.purchaselist

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_purchase_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                this.context, resources.configuration.orientation
            )
        )
        recycler_view.adapter = purchaseListAdapter
        recycler_view.layoutManager = LinearLayoutManager(context)
        arguments?.getString("party_id")?.let { purchaseListPresenter.showPurchase(it) }
        initClickListeners()
        swipe_container.setColorSchemeResources(R.color.colorAccent)
        swipe_container.setOnRefreshListener(this)
    }

    private fun initToolbar() {
        val activity = (activity as PartyDetailsActivity)
        activity.setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.purchase_list)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp)
        toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        val activity = (activity as PartyDetailsActivity)
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

    fun getFilterList(textQuery: String){
        purchaseListAdapter.list = ArrayList(localPurchase.filter { it.title.contains(textQuery) })
        purchaseListAdapter.notifyDataSetChanged()
    }

    private fun initClickListeners() {
        var purchaseId = arguments?.getString("party_id") ?: ""
        btn_add.setOnClickListener {
            fragmentManager?.let {
                it.beginTransaction()
                    .replace(R.id.container, AddPurchaseFragment.newInstance(purchaseId))
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun onItemClick(purchase: Purchase) {
        arguments?.getString("party_id")?.let { partyId ->
            fragmentManager?.let {
                it.beginTransaction()
                    .replace(R.id.container, PurchaseDetailsFragment.newInstance(partyId, purchase.title))
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun onRefresh() {
        arguments?.getString("party_id")?.let { purchaseListPresenter.showPurchase(it) }
    }

    override fun showPurchaseList(dataList: ArrayList<Purchase>) {
        localPurchase = dataList
        purchaseListAdapter.list = dataList
        purchaseListAdapter.notifyDataSetChanged()
        swipe_container.isRefreshing = false
    }

    companion object {
        var localMenu: Menu? = null
        var localPurchase = ArrayList<Purchase>()
        fun newInstance(partyId: String): PurchaseListFragment {
            val args = Bundle()
            args.putString("party_id", partyId)
            val fragment = PurchaseListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
