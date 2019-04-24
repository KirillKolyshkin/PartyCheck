package com.example.partycheckapp.presentation.feature.debtors.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.partycheckapp.R
import com.example.partycheckapp.data.debtors.Debtor
import com.example.partycheckapp.data.debtors.UserDebtor
import com.example.partycheckapp.data.party.Party
import com.example.partycheckapp.data.user.User

class DebtorsListAdapter : RecyclerView.Adapter<ViewHolder>() {

    var list: ArrayList<UserDebtor> = arrayListOf()
    override fun getItemCount() = list.size

    fun addAll(values: List<UserDebtor>) {
        for (value in values) {
            list.add(value)
            notifyItemInserted(list.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(list[position])
        //holder.itemView.setOnClickListener { onItemClick(list[position]) }
    }
}