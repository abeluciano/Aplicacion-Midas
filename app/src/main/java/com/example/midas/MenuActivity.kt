package com.example.midas

import android.content.Intent
import android.os.Bundle
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
import com.example.midas.Reportes.RealizarReporteActivity
import com.example.midas.Transferencia.TransferenciaActivity
import kotlin.properties.Delegates

class MenuActivity : AppCompatActivity() {
    private lateinit var idCuentaTextView:TextView
    private lateinit var saldoTextView:TextView
    private  var cuenta: Cuenta? = null
    private  var idUsuario by Delegates.notNull<Int>()
    private lateinit var accountAdapter:AccountAdapter
    private lateinit var dbHelper: DatabaseHelper
    private var idCuenta: String = ""
    private var tipoMoneda: String = ""
    private var saldo: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        dbHelper = DatabaseHelper(this)

        idCuentaTextView = findViewById(R.id.txtID)
        saldoTextView = findViewById(R.id.txtMostrarSaldo)
        val AbrirCuenta = findViewById<ImageButton>(R.id.btnAperturarCuenta)
        val Recarga = findViewById<ImageButton>(R.id.btnRecarga)
        val Transferencia = findViewById<ImageButton>(R.id.btnTranferencia)
        val Reporte = findViewById<ImageButton>(R.id.btnReporte)
        val Historial = findViewById<ImageButton>(R.id.btnHistorial)

        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        idUsuario = sharedPreferences.getInt("Id_Usuario", -1)
        initRecyclerView()
        if (idUsuario != -1) {
            cuenta = dbHelper.getFirstUserAccount(idUsuario.toString())
            if (cuenta != null) {
                idCuenta = cuenta!!.idCuenta
                saldo = cuenta!!.saldo
                tipoMoneda = cuenta!!.tipoMoneda

                idCuentaTextView.text = "ID Cuenta: $idCuenta"
                val saldoFormatted = String.format("%.2f", saldo)
                saldoTextView.text = "Saldo: ${if (tipoMoneda == "Soles") "S/" else "$"} $saldoFormatted"
            } else {
                idCuentaTextView.text = "No se encontró ninguna cuenta"
                saldoTextView.text = "Saldo: N/A"
            }
        }
        AbrirCuenta.setOnClickListener(){
            val intent = Intent(this, AperturarCuentaActivity::class.java)
            startActivity(intent)
        }


        Transferencia.setOnClickListener(){
            val intent = Intent(this, TransferenciaActivity::class.java)
            intent.putExtra("ID_CUENTA", idCuenta)
            intent.putExtra("TIPO_MONEDA", tipoMoneda)
            startActivity(intent)
        }
        Recarga.setOnClickListener {
            if (dbHelper.getNumeroCuentasUsuario(idUsuario.toString()) > 0) {
                val intent = Intent(this, RecargarSaldoActivity::class.java)
                intent.putExtra("ID_CUENTA", idCuenta)
                intent.putExtra("TIPO_MONEDA", tipoMoneda)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Debe crear al menos una cuenta para recargar saldo", Toast.LENGTH_SHORT).show()
            }
        }
        Reporte.setOnClickListener() {
            val intent = Intent(this, RealizarReporteActivity::class.java)
            startActivity(intent)
        }
        Historial.setOnClickListener() {
            val intent = Intent(this, HistoryActivity::class.java)
            intent.putExtra("ID_CUENTA", idCuenta)
            startActivity(intent)
        }
    }
    fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val list_accounts = dbHelper.getCuentasByUsuario(idUsuario.toString())
        accountAdapter = AccountAdapter(this, list_accounts) { cuenta ->
            onItemSelected(cuenta)
        }

        val decoration = DividerItemDecoration(this,manager.orientation)
        val usersRecycler = this.findViewById<RecyclerView>(R.id.recyclerView)
        usersRecycler.layoutManager = manager
        usersRecycler.adapter = accountAdapter
        usersRecycler.addItemDecoration(decoration)
    }
    private fun onItemSelected(cuenta: Cuenta){
        this.idCuenta = cuenta.idCuenta
        this.saldo = cuenta.saldo
        this.tipoMoneda = cuenta.tipoMoneda
        idCuentaTextView.text = "ID Cuenta: $idCuenta"
        val saldoFormatted = String.format("%.2f", saldo)
        saldoTextView.text = "Saldo: ${if (tipoMoneda == "Soles") "S/" else "$"} $saldoFormatted"
    }
}