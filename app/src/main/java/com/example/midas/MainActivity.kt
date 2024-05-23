package com.example.midas

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
                val addEstudiante:PreparedStatement = connecySQL.dbConn()?.prepareStatement("Insert into Estudiante(nombre, apellido) values (?,?)")!!
                addEstudiante.setString(1,nombre.text.toString())
                addEstudiante.setString(2,codigo.text.toString())
                addEstudiante.executeUpdate()
                Toast.makeText(this, "Estudiante ingresado correctamente", Toast.LENGTH_SHORT).show()
            }catch (ex: SQLException){
                Toast.makeText(this, "Error al ingresar", Toast.LENGTH_SHORT).show()
            }
        }





    }
}