package com.example.midas.AdapterAccount

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.DatasClass.Cuenta
import com.example.midas.R

class AccountAdapter(
    private val context: Context,
    private val accountList: List<Cuenta>,
    private val onAccountSelected: (Cuenta) -> Unit
) : RecyclerView.Adapter<AccountViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.account_item, parent, false)
        return AccountViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val account = accountList[position]
        holder.accountIdTextView.text = account.idCuenta.toString()
        holder.accountTypeTextView.text = account.tipoMoneda
        holder.accountBalanceTextView.text = account.saldo.toString()

        holder.itemView.setOnClickListener {
            onAccountSelected(account)
        }
    }

    override fun getItemCount(): Int {
        return accountList.size
    }
}
