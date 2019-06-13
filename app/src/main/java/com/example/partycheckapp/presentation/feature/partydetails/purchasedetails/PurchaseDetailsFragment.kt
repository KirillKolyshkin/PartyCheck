package com.example.partycheckapp.presentation.feature.partydetails.purchasedetails

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.partycheckapp.PartyApp
import com.example.partycheckapp.R
import com.example.partycheckapp.data.party.Purchase
import com.example.partycheckapp.data.user.User
import com.example.partycheckapp.presentation.feature.partydetails.PartyDetailsActivity
import com.example.partycheckapp.presentation.feature.partydetails.purchaselist.PurchaseListFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_purchase_details.*
import javax.inject.Inject

class PurchaseDetailsFragment : MvpAppCompatFragment(), PurchaseDetailsView {

    @Inject
    @InjectPresenter
    lateinit var purchaseDetailsPresenter: PurchaseDetailsPresenter

    @ProvidePresenter
    fun initPresenter() = purchaseDetailsPresenter

    private val purchaseDetailsAdapter = PurchaseDetailsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        PartyApp.instance
            .getAppComponent()
            .partyDetailsComponent()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_purchase_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        recycler_view.adapter = purchaseDetailsAdapter
        recycler_view.layoutManager = LinearLayoutManager(context)
        val partyId = arguments?.getString("party_id") ?: ""
        val title = arguments?.getString("title") ?: ""
        purchaseDetailsPresenter.getPurchase(partyId, title)
    }

    private fun initToolbar() {
        val activity = (activity as PartyDetailsActivity)
        activity.setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.purchase_details)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp)
        toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }
    }

    override fun initPurchase(purchase: Purchase) {
        tv_purchase_name.text = purchase.title
        tv_creditor_name.text = purchase.creditor.name
        tv_price.text = MONET_FORMAT.format(purchase.price)
        Picasso.with(context).load(purchase.imageUrl).into(iv_purchase_icon)
        purchaseDetailsAdapter.cost = purchase.price / purchase.userList.count()
        purchaseDetailsAdapter.list = purchase.userList as ArrayList<User>
        purchaseDetailsAdapter.notifyDataSetChanged()
    }

    companion object {
        private const val MONET_FORMAT = "%.2f $"
        fun newInstance(partyId: String, title: String): PurchaseDetailsFragment {
            val args = Bundle()
            args.putString("party_id", partyId)
            args.putString("title", title)
            val fragment = PurchaseDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
