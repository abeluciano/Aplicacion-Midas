/**
 * Actividad principal que muestra el menú de opciones para el usuario en la aplicación Midas.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * Esta actividad permite al usuario realizar diversas acciones como abrir una nueva cuenta,
 * realizar transferencias, recargar saldo, generar reportes, ver el historial de transacciones
 * y cerrar sesión. También muestra información actualizada de la cuenta seleccionada y lista
 * todas las cuentas asociadas al usuario utilizando un RecyclerView y un adaptador personalizado
 * (AccountAdapter). Al seleccionar una cuenta de la lista, se actualiza la información de la
 * cuenta mostrada en la parte superior.
 */

package com.example.midas

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.midas.AdapterAccount.AccountAdapter
import com.example.midas.BD.DatabaseHelper
import com.example.midas.DatasClass.Cuenta
import com.example.midas.Login.MainActivity
import com.example.midas.Recarga.RecargarSaldoActivity
import com.example.midas.Reportes.LlenarReporteActivity
import com.example.midas.Transferencia.TransferenciaActivity
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.Delegates

class MenuActivity : AppCompatActivity() {
    private lateinit var idCuentaTextView: TextView
    private lateinit var saldoTextView: TextView
    private var cuenta: Cuenta? = null
    private var idUsuario by Delegates.notNull<Int>()
    private lateinit var accountAdapter: AccountAdapter
    private lateinit var dbHelper: DatabaseHelper
    private var idCuenta: String = ""
    private var tipoMoneda: String = ""
    private var saldo: Double = 0.0
    private lateinit var tipo:TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        dbHelper = DatabaseHelper(this)

        idCuentaTextView = findViewById(R.id.txtID)
        saldoTextView = findViewById(R.id.txtMostrarSaldo)
        tipo = findViewById(R.id.Tipo)
        val AbrirCuenta = findViewById<ImageButton>(R.id.btnAperturarCuenta)
        val Recarga = findViewById<ImageButton>(R.id.btnRecarga)
        val Transferencia = findViewById<ImageButton>(R.id.btnTranferencia)
        val Reporte = findViewById<ImageButton>(R.id.btnReporte)
        val Historial = findViewById<ImageButton>(R.id.btnHistorial)
        val Logout = findViewById<ImageButton>(R.id.btnLogout)

        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        idUsuario = sharedPreferences.getInt("Id_Usuario", -1)
        idCuenta = sharedPreferences.getString("Id_Cuenta_Seleccionada", "") ?: ""

        AbrirCuenta.setOnClickListener {
            val intent = Intent(this, AperturarCuentaActivity::class.java)
            intent.putExtra("ID_CUENTA", idCuenta)
            intent.putExtra("ID_USUARIO", idUsuario)
            startActivity(intent)
        }

        Transferencia.setOnClickListener {
            if (dbHelper.getNumeroCuentasUsuario(idUsuario.toString()) > 0 && dbHelper.getEstadoCuentaById(idCuenta.toString()) == "Activa") {
                val intent = Intent(this, TransferenciaActivity::class.java)
                intent.putExtra("ID_CUENTA", idCuenta)
                intent.putExtra("TIPO_MONEDA", tipoMoneda)
                intent.putExtra("ID_USUARIO", idUsuario.toString())
                startActivity(intent)
            } else {
                if (dbHelper.getEstadoCuentaById(idCuenta.toString()) != "Activa") {
                    showInvalidEmailNotification("No puede realizar Transferencias con esta cuenta")
                } else if(dbHelper.getNumeroCuentasUsuario(idUsuario.toString()) == 0){
                    showInvalidEmailNotification("Debe crear al menos una cuenta para transferir")
                }
                return@setOnClickListener
            }
        }
        Recarga.setOnClickListener {
            if (dbHelper.getNumeroCuentasUsuario(idUsuario.toString()) > 0) {
                val intent = Intent(this, RecargarSaldoActivity::class.java)
                intent.putExtra("ID_CUENTA", idCuenta)
                intent.putExtra("TIPO_MONEDA", tipoMoneda)
                startActivity(intent)
            } else {
                showInvalidEmailNotification("Debe crear al menos una cuenta para recargar saldo")
                return@setOnClickListener
            }
        }
        Reporte.setOnClickListener {
            val intent = Intent(this, LlenarReporteActivity::class.java)
            intent.putExtra("ID_USUARIO", idUsuario.toString())
            startActivity(intent)
        }

        Historial.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            intent.putExtra("ID_CUENTA", idCuenta)
            startActivity(intent)
        }

        Logout.setOnClickListener {
            logout()
        }
    }

    override fun onResume() {
        super.onResume()
        updateAccountInfo()
        initRecyclerView()
    }

    private fun updateAccountInfo() {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        val idCuentaSeleccionada = sharedPreferences.getString("Id_Cuenta_Seleccionada", "")

        cuenta = if (!idCuentaSeleccionada.isNullOrEmpty()) {
            dbHelper.getAccountById(idCuentaSeleccionada)
        } else {
            dbHelper.getFirstUserAccount(idUsuario.toString())
        }

        if (cuenta != null) {
            idCuenta = cuenta!!.idCuenta
            saldo = cuenta!!.saldo
            tipoMoneda = cuenta!!.tipoMoneda

            tipo.text = "${if(tipoMoneda == "Soles") "Soles" else "Dolares"}"
            idCuentaTextView.text = "$idCuenta"
            val saldoFormatted = String.format("%.2f", saldo)
            saldoTextView.text = "${if (tipoMoneda == "Soles") "S/" else "$"} $saldoFormatted"
        } else {
            idCuentaTextView.text = "No se encontró ninguna cuenta"
            saldoTextView.text = "N/A"
            tipo.text = "N/A"
        }
    }


    private fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val list_accounts = dbHelper.getCuentasByUsuario(idUsuario.toString())
        accountAdapter = AccountAdapter(this, list_accounts) { cuenta ->
            onItemSelected(cuenta)
        }
        val decoration = DividerItemDecoration(this, manager.orientation)
        val usersRecycler = findViewById<RecyclerView>(R.id.recyclerView)
        usersRecycler.layoutManager = manager
        usersRecycler.adapter = accountAdapter
        usersRecycler.addItemDecoration(decoration)
    }

    private fun onItemSelected(cuenta: Cuenta) {
        if (cuenta.estado == "Congelada") {
            initDialog(cuenta)
        } else {
            this.idCuenta = cuenta.idCuenta
            this.saldo = cuenta.saldo
            this.tipoMoneda = cuenta.tipoMoneda

            val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("Id_Cuenta_Seleccionada", idCuenta)
            editor.apply()

            idCuentaTextView.text = "$idCuenta"
            val saldoFormatted = String.format("%.2f", saldo)
            saldoTextView.text = "${if (tipoMoneda == "Soles") "S/" else "$"} $saldoFormatted"
            tipo.text = "${if (tipoMoneda == "Soles") "Soles" else "Dolares"}"
        }
    }

    private fun initDialog(cuenta: Cuenta) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_advertenciacambio)
        val editTextRazon = dialog.findViewById<TextView>(R.id.editTextRazon)
        val btnAceptar = dialog.findViewById<Button>(R.id.btnok)
        val btnCancelar = dialog.findViewById<Button>(R.id.btnNo)
        editTextRazon.text = cuenta.razon

        btnAceptar.setOnClickListener {
            this.idCuenta = cuenta.idCuenta
            this.saldo = cuenta.saldo
            this.tipoMoneda = cuenta.tipoMoneda

            val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("Id_Cuenta_Seleccionada", idCuenta)
            editor.apply()

            idCuentaTextView.text = "$idCuenta"
            val saldoFormatted = String.format("%.2f", saldo)
            saldoTextView.text = "${if (tipoMoneda == "Soles") "S/" else "$"} $saldoFormatted"
            tipo.text = "${if (tipoMoneda == "Soles") "Soles" else "Dolares"}"
            dialog.dismiss()
        }
        btnCancelar.setOnClickListener{ dialog.dismiss() }

        dialog.show()
    }

    private fun logout() {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    @SuppressLint("RestrictedApi")
    private fun showInvalidEmailNotification(msg: String) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG)
        val customSnackView: View = layoutInflater.inflate(R.layout.custom_snackbar, null)
        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
        val snackbar_title = customSnackView.findViewById<TextView>(R.id.snackbar_title)
        val snackbar_text = customSnackView.findViewById<TextView>(R.id.snackbar_text)
        snackbar_text.text = msg
        snackbar_title.text = dbHelper.getNombreUsuarioByCuenta(idCuenta)
        snackbarLayout.removeAllViews()
        snackbarLayout.addView(customSnackView)
        snackbar.show()
    }
}
