package com.example.partycheckapp.presentation.feature.search.party

import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.partycheckapp.data.party.Party
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.party_item.view.*
import java.text.SimpleDateFormat

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindViews(party: Party) {
        with(itemView) {
            tv_party_name.text = party.title
            val sdf = SimpleDateFormat("dd/M/yyyy")
            val currentDate = sdf.format(party.date)
            tv_date.text = currentDate
            tv_org_name.text = party.owner.name
            tv_friends_amount.text = USERS_COUNT.format(party.users.count())
            try {
                val uri: Uri = Uri.parse(party.imageUrl)
                Picasso.with(context).load(uri).into(iv_party_icon)
            } catch (e: Exception) { }
        }
    }

    companion object {
        private const val USERS_COUNT = "%d users here"
    }
}
