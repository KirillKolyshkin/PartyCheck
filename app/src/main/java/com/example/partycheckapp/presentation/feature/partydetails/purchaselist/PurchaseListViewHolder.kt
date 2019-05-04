package com.example.partycheckapp.presentation.feature.partydetails.purchaselist

import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.partycheckapp.data.party.Purchase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.purchase_item.view.*

class PurchaseListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindViews(purchase: Purchase) {
        with(itemView) {
            tv_purchase_name.text = purchase.title
            tv_creditor_name.text = purchase.creditor.name
            tv_price.text = USERS_COUNT.format(purchase.price)
            try {
                val uri: Uri = Uri.parse(purchase.imageUrl)
                Picasso.with(context).load(uri).into(iv_purchase_icon)
            } catch (e: Exception) { }
        }
    }

    companion object {
        private const val USERS_COUNT = "%f0.2"
    }
}
