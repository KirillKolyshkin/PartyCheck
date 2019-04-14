package com.example.partycheckapp.presentation.feature.party.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.partycheckapp.data.party.Party
import kotlinx.android.synthetic.main.party_item.view.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindViews(party: Party) {
        itemView.tv_party_name.text = party.title
        itemView.tv_friends_amount.text = party.participantList.size.toString()
        itemView.tv_org_name.text = party.organization
    }
}
