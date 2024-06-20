
/**
 * ViewHolder para una cuenta en un RecyclerView.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * Esta clase AccountViewHolder representa una vista de un solo elemento (Cuenta) en un
 * RecyclerView. Contiene referencias a las vistas dentro de cada elemento y un método
 * para vincular los datos de una cuenta específica a estas vistas. También maneja los
 * eventos de clic en cada elemento.
 */
package com.example.midas.AdapterAccount

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.DatasClass.Cuenta
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