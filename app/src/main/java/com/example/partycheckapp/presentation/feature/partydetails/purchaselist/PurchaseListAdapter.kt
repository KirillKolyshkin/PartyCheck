package com.example.partycheckapp.presentation.feature.partydetails.purchaselist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.partycheckapp.R
import com.example.partycheckapp.data.party.Purchase

class PurchaseListAdapter (private var onItemClick: (Purchase) -> Unit) : RecyclerView.Adapter<PurchaseListViewHolder>() {

    var list: ArrayList<Purchase> = arrayListOf()
    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseListViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.purchase_item, parent, false)
        return PurchaseListViewHolder(v)
    }

    override fun onBindViewHolder(holder: PurchaseListViewHolder, position: Int) {
        holder.bindViews(list[position])
        holder.itemView.setOnClickListener { onItemClick(list[position]) }
    }
}

