package com.example.midas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.sql.PreparedStatement
import java.sql.SQLException


class MainActivity : AppCompatActivity() {
    private var connecySQL= AccesoDatos()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var nombre = findViewById<EditText>(R.id.txtUsuario)
        var codigo = findViewById<EditText>(R.id.txtClave)
        var botonEnviar = findViewById<Button>(R.id.btnIniciar)
        botonEnviar.setOnClickListener(){

            try {
                val checkUsuario: PreparedStatement = connecySQL.dbConn()?.prepareStatement("SELECT * FROM Usuario WHERE nombre = ? AND contraseña = ?")!!
                checkUsuario.setString(1, nombre.text.toString())
                checkUsuario.setString(2, codigo.text.toString())

                val resultSet = checkUsuario.executeQuery()

                if (resultSet.next()) {
                    // El estudiante existe
                    val userId = resultSet.getInt("Id_Usuario")
                    val contraseñas = resultSet.getString("contraseña")
                    Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
                    val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putInt("Id_Usuario", userId)
                    editor.apply()

                    val intent = Intent(this, Menu::class.java)
                    startActivity(intent)
                } else {
                    // El estudiante no existe
                    Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                }

            } catch (ex: SQLException) {
                Toast.makeText(this, "Error al verificar", Toast.LENGTH_SHORT).show()
            }


        }





    }
}