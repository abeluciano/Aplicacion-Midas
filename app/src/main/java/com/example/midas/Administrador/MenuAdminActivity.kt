package com.example.midas.Administrador

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.midas.Login.MainActivity
import com.example.midas.R
import com.example.midas.SignUpActivity

class MenuAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_admin)

        val btnUsers = findViewById<ImageButton>(R.id.btnUsers)
        val btnGesReportes = findViewById<ImageButton>(R.id.btnGesReportes)
        val Logout = findViewById<ImageButton>(R.id.btnLogout)

        btnUsers.setOnClickListener(){
            val intent = Intent(this, GestionarUsuariosActivity::class.java)
            startActivity(intent)
        }

        btnGesReportes.setOnClickListener(){
            val intent = Intent(this, GestionarReportesActivity::class.java)
            startActivity(intent)
        }

        Logout.setOnClickListener {
            logout()
        }

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
}