package com.example.partycheckapp.presentation.feature.party.partylist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.partycheckapp.data.party.Party
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.party_item_debt.view.*
import java.text.SimpleDateFormat

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindViews(party: Party) {
        with(itemView) {
            tv_party_name.text = party.title
            val sdf = SimpleDateFormat("dd/M/yyyy")
            val currentDate = sdf.format(party.date)
            tv_date.text = currentDate
            tv_friends_amount.text = USERS_COUNT.format(party.users.count())
            Picasso.with(context).load(party.imageUrl).into(iv_party_icon)
        }
    }

    companion object {
        private const val USERS_COUNT = "%d users here"
    }
}
