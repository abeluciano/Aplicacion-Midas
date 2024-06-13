package com.example.midas.Adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.Cuenta
import com.example.midas.R


class AccountViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val accountIdTextView = itemView.findViewById<TextView>(R.id.accountIdTextView)
    val accountTypeTextView = itemView.findViewById<TextView>(R.id.accountTypeTextView)
    val accountBalanceTextView = itemView.findViewById<TextView>(R.id.accountBalanceTextView)
    fun render(item: Cuenta, l: View.OnClickListener, onClickListener: (Cuenta) -> Unit) {
        accountIdTextView.text = item.idCuenta
        accountTypeTextView.text = item.tipoMoneda
        accountBalanceTextView.text = item.saldo.toString()
        itemView.setOnClickListener {
            onClickListener(item)
        }
    }
}