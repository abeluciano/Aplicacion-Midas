package com.example.midas

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private var idCuenta: String = ""
    private var tipoMoneda: String = ""
    private var saldo: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        dbHelper = DatabaseHelper(this)

        val idCuentaTextView = findViewById<TextView>(R.id.txtID)
       val  saldoTextView = findViewById<TextView>(R.id.txtMostrarSaldo)
        val AbrirCuenta = findViewById<ImageButton>(R.id.btnAperturarCuenta)
        val Recarga = findViewById<ImageButton>(R.id.btnRecarga)
        val Transferencia = findViewById<ImageButton>(R.id.btnTranferencia)

        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        val idUsuario = sharedPreferences.getInt("Id_Usuario", -1)
        if (idUsuario != -1) {
            val cuenta = dbHelper.getFirstUserAccount(idUsuario.toString())
            if (cuenta != null) {
                idCuenta = cuenta.idCuenta
                saldo = cuenta.saldo
                tipoMoneda = cuenta.tipoMoneda

                idCuentaTextView.text = "ID Cuenta: $idCuenta"
                saldoTextView.text = "Saldo: ${if (tipoMoneda == "Soles") "S/" else "$"} $saldo"
            } else {
                idCuentaTextView.text = "No se encontró ninguna cuenta"
                saldoTextView.text = "Saldo: N/A"
            }
        }
        AbrirCuenta.setOnClickListener(){
            val intent = Intent(this, AperturarCuentaActivity::class.java)
            startActivity(intent)
            finish()
        }


        Transferencia.setOnClickListener(){
            val intent = Intent(this, TransferenciaActivity::class.java)
            startActivity(intent)
            finish()
        }
        Recarga.setOnClickListener {
            val intent = Intent(this, RecargarSaldoActivity::class.java)
            intent.putExtra("ID_CUENTA", idCuenta)
            intent.putExtra("TIPO_MONEDA", tipoMoneda)
            startActivity(intent)
            finish()
        }
    }
}