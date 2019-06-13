package com.example.partycheckapp.presentation.feature.partydetails.purchasedetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.partycheckapp.R
import com.example.partycheckapp.data.user.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_item.view.*

class PurchaseDetailsAdapter : RecyclerView.Adapter<PurchaseDetailsAdapter.PurchaseDetailsViewHolder>() {

    var list: ArrayList<User> = arrayListOf()
    var cost: Double = 0.0
    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseDetailsViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return PurchaseDetailsViewHolder(v)
    }

    override fun onBindViewHolder(holder: PurchaseDetailsViewHolder, position: Int) {
        val currentUser = list[position]
        holder.bindViews(currentUser)
    }

    inner class PurchaseDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindViews(user: User) {
            with(itemView) {
                tv_name.text = user.name
                tv_phone_num.text = user.phoneNumber
                tv_money_amount.text = MONET_FORMAT.format(cost)
                Picasso.with(context).load(user.imageUrl).into(iv_user_icon)
            }
        }
    }

    companion object {
        private const val MONET_FORMAT = "%.2f $"
    }
}
