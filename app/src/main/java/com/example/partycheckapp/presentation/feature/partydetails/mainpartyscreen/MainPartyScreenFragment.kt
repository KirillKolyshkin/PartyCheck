package com.example.partycheckapp.presentation.feature.partydetails.mainpartyscreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.partycheckapp.PartyApp
import com.example.partycheckapp.R
import com.example.partycheckapp.data.party.Party
import com.example.partycheckapp.presentation.feature.main.activity.MainActivity
import com.example.partycheckapp.presentation.feature.partydetails.PartyDetailsActivity
import com.example.partycheckapp.presentation.feature.partydetails.purchaselist.PurchaseListFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_party_details.*
import java.text.SimpleDateFormat
import javax.inject.Inject
import com.google.android.material.appbar.CollapsingToolbarLayout

class MainPartyScreenFragment : MvpAppCompatFragment(), MainPartyScreenView {

    @Inject
    @InjectPresenter
    lateinit var mainPartyScreenPresenter: MainPartyScreenPresenter

    @ProvidePresenter
    fun initPresenter() = mainPartyScreenPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        PartyApp.instance
            .getAppComponent()
            .partyDetailsComponent()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_party_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("party_id")?.let { mainPartyScreenPresenter.getParty(it) }
        initClickListeners()
    }

    private fun initToolbar(title: String) {
        val activity = (activity as PartyDetailsActivity)
        activity.setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp)
        activity.supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { activity.onBackPressed() }

        val collapsingToolbar = collapse_toolbar as CollapsingToolbarLayout
        collapsingToolbar.title = title
        val color = context?.let { ContextCompat.getColor(it, R.color.colorWhite) }
        color?.let {
            collapsingToolbar.setExpandedTitleColor(it)
            collapsingToolbar.setCollapsedTitleTextColor(it)
        }
    }

    private fun initClickListeners() {
        val purchaseId = arguments?.getString("party_id") ?: ""
        btn_add.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.container, PurchaseListFragment.newInstance(purchaseId))
                ?.addToBackStack(null)?.commit()
        }
    }

    override fun initView(party: Party) {
        initToolbar(party.title)
        Picasso.with(context).load(party.imageUrl).into(iv_party)
        tv_description.text = party.description
        tv_place.text = party.location
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(party.date)
        tv_date.text = currentDate
    }

    companion object {
        fun newInstance(partyId: String): MainPartyScreenFragment {
            val args = Bundle()
            args.putString("party_id", partyId)
            val fragment = MainPartyScreenFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
