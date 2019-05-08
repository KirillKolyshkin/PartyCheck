package com.example.partycheckapp.presentation.feature.debtors

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.partycheckapp.R
import com.example.partycheckapp.data.debtors.UserDebtor
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_item.view.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindViews(user: UserDebtor) {
        with(itemView) {
            tv_name.text = user.name
            tv_phone_num.text = user.phoneNumber
            Picasso.with(context).load(user.imageUrl).into(iv_user_icon)

            val colorRes = if (user.debtSize < 0) {
                R.color.colorButton
            } else {
                R.color.colorGreenNumber
            }
            val color = ContextCompat.getColor(itemView.context, colorRes)
            tv_money_amount.setTextColor(color)
            tv_money_amount.text = MONET_FORMAT.format(user.debtSize)

        }
    }

    companion object {
        private const val MONET_FORMAT = "%.2f $"
    }
}
