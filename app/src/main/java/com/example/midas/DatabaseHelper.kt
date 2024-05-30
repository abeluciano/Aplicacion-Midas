package com.example.midas

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "MidasBD.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_USUARIO = "Usuario"
        private const val COLUMN_ID_USUARIO = "Id_Usuario"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_CONTRASEÑA = "contraseña"
        private const val COLUMN_CORREO = "correo"

        private const val TABLE_CUENTA = "Cuenta"
        private const val COLUMN_ID_CUENTA = "Id_Cuenta"
        private const val COLUMN_TIPO_CUENTA = "Tipo_Cuenta"
        private const val COLUMN_SALDO = "Saldo"
        private const val COLUMN_ESTADO_CUENTA = "Estado_Cuenta"
        private const val COLUMN_ID_USUARIO_FK = "Id_Usuario"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUsuarioTable = ("CREATE TABLE " + TABLE_USUARIO + "("
                + COLUMN_ID_USUARIO + " TEXT PRIMARY KEY,"
                + COLUMN_NOMBRE + " TEXT,"
                + COLUMN_CONTRASEÑA + " TEXT,"
                + COLUMN_CORREO + " TEXT" + ")")
        db.execSQL(createUsuarioTable)

        val createCuentaTable = ("CREATE TABLE " + TABLE_CUENTA + "("
                + COLUMN_ID_CUENTA + " INTEGER PRIMARY KEY,"
                + COLUMN_TIPO_CUENTA + " TEXT,"
                + COLUMN_SALDO + " DECIMAL,"
                + COLUMN_ESTADO_CUENTA + " TEXT,"
                + COLUMN_ID_USUARIO_FK + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_ID_USUARIO_FK + ") REFERENCES " + TABLE_USUARIO + "(" + COLUMN_ID_USUARIO + "))")
        db.execSQL(createCuentaTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CUENTA")
        onCreate(db)
    }

    fun addUser(idUsuario: String, nombre: String, contraseña: String, correo: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID_USUARIO, idUsuario)
        values.put(COLUMN_NOMBRE, nombre)
        values.put(COLUMN_CONTRASEÑA, contraseña)
        values.put(COLUMN_CORREO, correo)
        db.insert(TABLE_USUARIO, null, values)
        db.close()
    }

    fun getUserPassword(idUsuario: String): String? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USUARIO,
            arrayOf(COLUMN_CONTRASEÑA),
            "$COLUMN_ID_USUARIO=?",
            arrayOf(idUsuario),
            null, null, null, null
        )
        cursor?.moveToFirst()
        val contraseña = cursor?.getString(0)
        cursor?.close()
        return contraseña
    }

    fun addCuenta(idCuenta: Int, tipoCuenta: String, idUsuario: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID_CUENTA, idCuenta)
        values.put(COLUMN_TIPO_CUENTA, tipoCuenta)
        values.put(COLUMN_SALDO, 0.0)
        values.put(COLUMN_ESTADO_CUENTA, "Activa")
        values.put(COLUMN_ID_USUARIO_FK, idUsuario)
        db.insert(TABLE_CUENTA, null, values)
        db.close()
    }

    fun verifyUser(IdUSer: String, contraseña: String): Int {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USUARIO,
            arrayOf(COLUMN_ID_USUARIO),
            "$COLUMN_ID_USUARIO=? AND $COLUMN_CONTRASEÑA=?",
            arrayOf(IdUSer, contraseña),
            null, null, null, null
        )

        if (cursor != null && cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(COLUMN_ID_USUARIO)
            if (columnIndex != -1) {
                val userId = cursor.getInt(columnIndex)
                cursor.close()
                return userId
            }
        }
        cursor?.close()
        return -1
    }

    fun getFirstUserAccount(idUsuario: String): Cuenta? {
        val db = readableDatabase
        val query = "SELECT Id_Cuenta, Saldo, Tipo_Cuenta FROM Cuenta WHERE Id_Usuario = ? LIMIT 1"
        val cursor = db.rawQuery(query, arrayOf(idUsuario))

        return if (cursor.moveToFirst()) {
            val idCuenta = cursor.getString(cursor.getColumnIndexOrThrow("Id_Cuenta"))
            val saldo = cursor.getDouble(cursor.getColumnIndexOrThrow("Saldo"))
            val tipoMoneda = cursor.getString(cursor.getColumnIndexOrThrow("Tipo_Cuenta"))
            cursor.close()
            Cuenta(idCuenta, saldo, tipoMoneda)
        } else {
            cursor.close()
            null
        }
    }
    fun isUserExists(idUsuario: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USUARIO WHERE $COLUMN_ID_USUARIO = ?"
        val cursor = db.rawQuery(query, arrayOf(idUsuario))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

}
