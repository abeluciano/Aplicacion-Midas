package com.example.midas

import android.os.StrictMode
import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class AccesoDatos {

    private val ip="192.168.0.17:1433"
    private val db="MidasBD"
    private val username="admin"
    private val password="admin123"

    fun dbConn(): Connection? {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        var conn: Connection? = null
        val connString:String
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance()
            connString = "jdbc:jtds:sqlserver://$ip;databaseName=$db;user=$username;password=$password"
            conn = DriverManager.getConnection(connString)
        }catch (ex: SQLException){
            ex.printStackTrace()
        }catch (ex1: ClassNotFoundException){
            ex1.printStackTrace()
        }catch (ex2:Exception){
            ex2.printStackTrace()
        }
        return conn
    }
}