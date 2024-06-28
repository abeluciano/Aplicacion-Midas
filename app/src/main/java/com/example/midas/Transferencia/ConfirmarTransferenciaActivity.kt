package com.example.midas.Transferencia

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.midas.BD.DatabaseHelper
import com.example.midas.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class ConfirmarTransferenciaActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private var idCuentaDestino: String = ""
    private var monto: String = ""
    private var idCuenta: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_transferencia)

        dbHelper = DatabaseHelper(this)
        val btnAtras = findViewById<ImageButton>(R.id.btnAtras)
        val txtNameDes = findViewById<TextView>(R.id.txtName)
        val txtCDes = findViewById<TextView>(R.id.txtCDes)
        val txtTipoMDes = findViewById<TextView>(R.id.txtTipo)
        val simboloMonedaTextView = findViewById<TextView>(R.id.simboloMonedaTextView)
        val Monto = findViewById<TextView>(R.id.Monto)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)

        idCuentaDestino = intent.getStringExtra("ID_CUENTADESTINO") ?: ""
        monto = intent.getStringExtra("MONTO") ?: ""
        idCuenta = intent.getStringExtra("ID_CUENTA") ?: ""

        val nombreUser = dbHelper.getNombreUsuarioByCuenta(idCuentaDestino)
        val tipoDestino = dbHelper.getTipoCuentaByCuenta(idCuentaDestino)
        val tipoOrigen = dbHelper.getTipoCuentaByCuenta(idCuenta)


        val montoConvertido = when {
            tipoOrigen == "Soles" && tipoDestino == "Dolares" -> monto.toDouble() * 0.26
            tipoOrigen == "Dolares" && tipoDestino == "Soles" -> monto.toDouble() * 3.79
            else -> monto.toDouble()
        }

        txtNameDes.text = " $nombreUser"
        txtCDes.text = " $idCuentaDestino"
        txtTipoMDes.text = " $tipoDestino"
        val simbolo = if (tipoDestino == "Soles") "S/" else "$"
        simboloMonedaTextView.text = simbolo
        Monto.text = montoConvertido.toString()



        btnConfirm.setOnClickListener {

            val saldo = dbHelper.getSaldoByCuenta(idCuenta)
            val saldoDes = dbHelper.getSaldoByCuenta(idCuentaDestino)
            val nuevoSaldoDes = saldoDes?.plus(montoConvertido)
            val nuevoSaldo = saldo?.minus(monto.toDouble())
            nuevoSaldo?.let { it1 -> dbHelper.updateSaldoByIdCuenta(idCuenta, it1) }
            nuevoSaldoDes?.let { it1 -> dbHelper.updateSaldoByIdCuenta(idCuentaDestino, it1) }
            val idTransferencia = Random.nextInt(100000000, 200000000)
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

            if (tipoDestino != null) {
                dbHelper.addTransferencia(
                    idTransferencia,
                    montoConvertido,
                    tipoDestino,
                    idCuentaDestino,
                    currentDate,
                    currentTime,
                    idCuenta
                )
            }

            val intent = Intent(this, TransferenciaExitosaActivity::class.java)
            intent.putExtra("NAME", nombreUser)
            intent.putExtra("SIMBOLO", simbolo)
            intent.putExtra("MONTO", montoConvertido.toString())
            intent.putExtra("FECHA", currentDate)
            intent.putExtra("HORA", currentTime)
            intent.putExtra("ID_CUENTA_DESTINO", idCuentaDestino)
            startActivity(intent)
            finish()

        }

        btnAtras.setOnClickListener {
            finish()
        }
    }
}
