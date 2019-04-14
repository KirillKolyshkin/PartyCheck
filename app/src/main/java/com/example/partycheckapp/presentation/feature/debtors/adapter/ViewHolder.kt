package com.example.partycheckapp.presentation.feature.debtors.adapter

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.partycheckapp.data.debtors.UserDebtor
import kotlinx.android.synthetic.main.user_item.view.*
import java.util.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindViews(user: UserDebtor) {
        itemView.tv_name.text = user.name
        itemView.tv_phone_num.text = user.phoneNumber.toString()
        if (user.debtSize < 0)
            itemView.tv_money_amount.setTextColor(Color.parseColor("#FF5722"))
        itemView.tv_money_amount.text = "$ " + String.format( Locale.US, "%.2f", user.debtSize)
    }
}
