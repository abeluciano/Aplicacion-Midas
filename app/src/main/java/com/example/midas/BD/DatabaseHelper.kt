package com.example.midas.BD

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.midas.DatasClass.Cuenta
import com.example.midas.DatasClass.Transferencia

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

        private const val TABLE_TRANSFERENCIA = "Transferencia"
        private const val COLUMN_ID_TRANSFERENCIA = "Id_Transferencia"
        private const val COLUMN_MONTO = "Monto"
        private const val COLUMN_TIPODES = "TipoMonedaDes"
        private const val COLUMN_CUENTA_DESTINO = "Cuenta_Destino"
        private const val COLUMN_FECHA = "Fecha"
        private const val COLUMN_HORA = "Hora"
        private const val COLUMN_ID_CUENTA_FK = "Id_Cuenta"
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

        val createTransferenciaTable =("CREATE TABLE " + TABLE_TRANSFERENCIA + "("
                + COLUMN_ID_TRANSFERENCIA + " INTEGER PRIMARY KEY,"
                + COLUMN_MONTO + " DECIMAL,"
                + COLUMN_TIPODES + " TEXT,"
                + COLUMN_CUENTA_DESTINO + " INTEGER,"
                + COLUMN_FECHA + " DATE,"
                + COLUMN_HORA + " TIME,"
                + COLUMN_ID_CUENTA_FK + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_ID_CUENTA_FK + ") REFERENCES " + TABLE_CUENTA + "(" + COLUMN_ID_CUENTA + "))")
        db.execSQL(createTransferenciaTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CUENTA")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSFERENCIA")
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

    fun verifyAccount(idCuenta: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_ID_CUENTA FROM $TABLE_CUENTA WHERE $COLUMN_ID_CUENTA = ?"
        val cursor = db.rawQuery(query, arrayOf(idCuenta))
        val exists = cursor.count > 0
        cursor.close()
        return exists
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
    fun getNumeroCuentasUsuario(idUsuario: String): Int {
        val db = this.readableDatabase
        val query = "SELECT COUNT(*) FROM Cuenta WHERE Id_Usuario = ?"
        val cursor = db.rawQuery(query, arrayOf(idUsuario))
        var numCuentas = 0

        if (cursor.moveToFirst()) {
            numCuentas = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return numCuentas
    }
    fun getCuentasByUsuario(idUsuario: String): List<Cuenta> {
        val cuentasList = mutableListOf<Cuenta>()
        val db = this.readableDatabase
        val query = "SELECT Id_Cuenta, Saldo, Tipo_Cuenta FROM $TABLE_CUENTA WHERE $COLUMN_ID_USUARIO_FK = ?"
        val cursor = db.rawQuery(query, arrayOf(idUsuario))

        if (cursor.moveToFirst()) {
            do {
                val idCuenta = cursor.getString(cursor.getColumnIndexOrThrow("Id_Cuenta"))
                val saldo = cursor.getDouble(cursor.getColumnIndexOrThrow("Saldo"))
                val tipoMoneda = cursor.getString(cursor.getColumnIndexOrThrow("Tipo_Cuenta"))
                cuentasList.add(Cuenta(idCuenta, saldo, tipoMoneda))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return cuentasList
    }

    fun getTransferenciasByCuenta(idCuenta: String): MutableList<Transferencia> {
        val transferenciasList = mutableListOf<Transferencia>()
        val db = this.readableDatabase
        val query = """
        SELECT t.Monto, t.Fecha, cOrigen.$COLUMN_ID_CUENTA AS Cuenta_Origen, uDestino.$COLUMN_NOMBRE AS Nombre_Destino
        FROM $TABLE_TRANSFERENCIA t
        JOIN $TABLE_CUENTA cOrigen ON t.$COLUMN_ID_CUENTA_FK = cOrigen.$COLUMN_ID_CUENTA
        JOIN $TABLE_CUENTA cDestino ON t.$COLUMN_CUENTA_DESTINO = cDestino.$COLUMN_ID_CUENTA
        JOIN $TABLE_USUARIO uDestino ON cDestino.$COLUMN_ID_USUARIO_FK = uDestino.$COLUMN_ID_USUARIO
        WHERE t.$COLUMN_ID_CUENTA_FK = ?
    """
        val cursor = db.rawQuery(query, arrayOf(idCuenta))

        if (cursor.moveToFirst()) {
            do {
                val monto = cursor.getDouble(cursor.getColumnIndexOrThrow("Monto"))
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow("Fecha"))
                val cuentaOrigen = cursor.getString(cursor.getColumnIndexOrThrow("Cuenta_Origen"))
                val nombreDestino = cursor.getString(cursor.getColumnIndexOrThrow("Nombre_Destino"))
                val transferencia = Transferencia(nombreDestino, monto, cuentaOrigen, fecha)
                transferenciasList.add(transferencia)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return transferenciasList
    }

    fun getNombreUsuarioByCuenta(idCuenta: String): String? {
        val db = this.readableDatabase
        val query = """
        SELECT u.$COLUMN_NOMBRE
        FROM $TABLE_CUENTA c
        JOIN $TABLE_USUARIO u ON c.$COLUMN_ID_USUARIO_FK = u.$COLUMN_ID_USUARIO
        WHERE c.$COLUMN_ID_CUENTA = ?
    """
        val cursor = db.rawQuery(query, arrayOf(idCuenta))

        return if (cursor.moveToFirst()) {
            val nombreUsuario = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
            cursor.close()
            nombreUsuario
        } else {
            cursor.close()
            null
        }
    }

    fun getTipoCuentaByCuenta(idCuenta: String): String? {
        val db = this.readableDatabase
        val query = """
        SELECT $COLUMN_TIPO_CUENTA
        FROM $TABLE_CUENTA
        WHERE $COLUMN_ID_CUENTA = ?
    """
        val cursor = db.rawQuery(query, arrayOf(idCuenta))

        return if (cursor.moveToFirst()) {
            val tipoCuenta = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPO_CUENTA))
            cursor.close()
            tipoCuenta
        } else {
            cursor.close()
            null
        }
    }

    fun addTransferencia(
        idTransferencia: Int,
        monto: Double,
        tipoMonedaDes: String,
        cuentaDestino: String,
        fecha: String,
        hora: String,
        idCuenta: String
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID_TRANSFERENCIA, idTransferencia)
        values.put(COLUMN_MONTO, monto)
        values.put(COLUMN_TIPODES, tipoMonedaDes)
        values.put(COLUMN_CUENTA_DESTINO, cuentaDestino)
        values.put(COLUMN_FECHA, fecha)
        values.put(COLUMN_HORA, hora)
        values.put(COLUMN_ID_CUENTA_FK, idCuenta)

        db.insert(TABLE_TRANSFERENCIA, null, values)
        db.close()
    }

}
