package com.example.partycheckapp.presentation.feature.party.adapter

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.partycheckapp.R
import com.example.partycheckapp.data.party.PartyWithDebt
import kotlinx.android.synthetic.main.party_item_debt.view.*
import java.text.SimpleDateFormat
import java.util.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindViews(party: PartyWithDebt) {
        with(itemView) {
            tv_party_name.text = party.title
            val sdf = SimpleDateFormat("dd/M/yyyy")
            val currentDate = sdf.format(party.date)
            tv_date.text = currentDate
            tv_org_name.text = party.organization
            val colorRes = if (party.debtSize < 0) {
                R.color.colorButton
            } else {
                R.color.colorGreenNumber
            }
            val color = ContextCompat.getColor(itemView.context, colorRes)
            tv_debt_size.setTextColor(color)
            tv_debt_size.text = MONET_FORMAT.format(party.debtSize)
        }
    }

    companion object {
        private const val MONET_FORMAT = "%.2f $"
    }
}
