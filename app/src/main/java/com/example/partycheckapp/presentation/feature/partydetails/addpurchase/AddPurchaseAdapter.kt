package com.example.partycheckapp.presentation.feature.partydetails.addpurchase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.partycheckapp.R
import com.example.partycheckapp.data.user.UserWithFlag
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_item_for_purchase.view.*

class AddPurchaseAdapter : RecyclerView.Adapter<AddPurchaseAdapter.AddPurchaseViewHolder>() {

    var list: ArrayList<UserWithFlag> = arrayListOf()
    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddPurchaseViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.user_item_for_purchase, parent, false)
        return AddPurchaseViewHolder(v)
    }

    override fun onBindViewHolder(holder: AddPurchaseViewHolder, position: Int) {
        val currentUser = list[position]
        holder.bindViews(currentUser)
        holder.itemView.checkbox.setOnCheckedChangeListener(null)
        holder.itemView.checkbox.isChecked = currentUser.flag
        holder.itemView
            .checkbox
            .setOnCheckedChangeListener(CompoundButton
                .OnCheckedChangeListener(fun(_: CompoundButton, isChecked: Boolean) {
            list[position].flag = isChecked
        }))
    }

    inner class AddPurchaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindViews(user: UserWithFlag) {
            with(itemView) {
                tv_name.text = user.name
                tv_phone_num.text = user.phoneNumber
                    Picasso.with(context).load(user.imageUrl).into(iv_user_icon)
            }
        }

    }
}
