package com.example.midas.Administrador

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.midas.R

class GestionarUsuariosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestionar_usuarios)

        val btnAtrasUsers = findViewById<ImageButton>(R.id.btnAtrasUsers)

        btnAtrasUsers.setOnClickListener(){
            finish()
        }
    }
}