package com.example.partycheckapp.presentation.feature.party.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.partycheckapp.R
import com.example.partycheckapp.data.party.PartyWithDebt

class PartyListAdapter : RecyclerView.Adapter<ViewHolder>() {

    var list: ArrayList<PartyWithDebt> = arrayListOf()
    override fun getItemCount() = list.size

    fun addAll(values: List<PartyWithDebt>) {
        for (value in values) {
            list.add(value)
            notifyItemInserted(list.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.party_item_debt, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(list[position])
        //holder.itemView.setOnClickListener { onItemClick(list[position]) }
    }
}