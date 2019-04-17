package com.example.partycheckapp.presentation.feature.search.party.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.partycheckapp.data.party.Party
import kotlinx.android.synthetic.main.party_item.view.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindViews(party: Party) {
        with(itemView) {
            tv_party_name.text = party.title
            tv_friends_amount.text = "${party.participantList.size} your friends here"
            tv_org_name.text = party.organization
        }
    }
}
