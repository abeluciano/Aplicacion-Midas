package com.example.midas.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.Cuenta
import com.example.midas.R

class AccountAdapter(
    private val context: Context,
    private val accountList: List<Cuenta>,
    private val onAccountSelected: (Cuenta) -> Unit
) : RecyclerView.Adapter<AccountViewHolder>() {

    /*inner class AccountViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val accountIdTextView: TextView = view.findViewById(R.id.accountIdTextView)
        val accountTypeTextView: TextView = view.findViewById(R.id.accountTypeTextView)
        val accountBalanceTextView: TextView = view.findViewById(R.id.accountBalanceTextView)
    }
*/
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
