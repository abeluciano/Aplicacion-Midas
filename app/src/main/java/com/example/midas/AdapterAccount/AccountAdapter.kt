/**
 * Adaptador para una lista de cuentas en un RecyclerView.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * Esta clase AccountAdapter es un adaptador personalizado para mostrar una lista de objetos
 * Cuenta en un RecyclerView. Se encarga de crear y vincular las vistas para cada cuenta en
 * la lista, y maneja los eventos de clic en cada elemento de la lista para seleccionar una cuenta.
 */

package com.example.midas.AdapterAccount

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
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
        holder.accountIdTextView.text = account.idCuenta
        holder.accountTypeTextView.text = account.tipoMoneda
        val saldoN = String.format("%.2f", account.saldo)
        holder.accountBalanceTextView.text = saldoN

        holder.itemView.setOnClickListener {
            onAccountSelected(account)
        }
        holder.btnCpoar.setOnClickListener(){
            val clipboard = ContextCompat.getSystemService( context,
                ClipboardManager::class.java
            ) as ClipboardManager
            val clip = ClipData.newPlainText("ID", account.idCuenta)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, "ID copiado al portapapeles", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return accountList.size
    }
}
