/**
 * Clase que gestiona la base de datos de la aplicación Midas.
 *
 * Autores:
 * Abel Luciano Aragón Alvaro
 * Josshua David Flores Chumbimuni
 * Rodrigo Ojeda Arce
 *
 * Resumen:
 * La clase DatabaseHelper es una implementación de SQLiteOpenHelper que maneja la creación,
 * actualización y operaciones básicas de la base de datos para la aplicación Midas.
 * Proporciona métodos para la gestión de usuarios, cuentas, transferencias y reportes.
 */
package com.example.midas.BD

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.midas.Administrador.DataClassAdmin.Cuentas
import com.example.midas.Administrador.DataClassAdmin.Reportes
import com.example.midas.Administrador.DataClassAdmin.Usuarios
import com.example.midas.DatasClass.Cuenta
import com.example.midas.DatasClass.Recarga
import com.example.midas.DatasClass.Reporte
import com.example.midas.DatasClass.Transferencia

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "MidasBD.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_ADMINISTRADOR = "Administrador"
        private const val COLUMN_ID_ADMINISTRADOR = "Id_Administrador"
        private const val COLUMN_NOMBRE_ADMIN = "nombre_Admin"
        private const val COLUMN_CONTRASEÑA_ADMIN = "contraseña_Admin"

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
        private const val COLUMN_RAZON = "Razon"
        private const val COLUMN_ID_USUARIO_FK = "Id_Usuario"

        private const val TABLE_RECARGA = "Recarga"
        private const val COLUMN_ID_RECARGA = "Id_Recarga"
        private const val COLUMN_MONTO_RECARGA = "Monto"
        private const val COLUMN_FECHA_RECARGA = "Fecha"
        private const val COLUMN_HORA_RECARGA = "Hora"
        private const val COLUMN_ID_CUENTA_RECARGA_FK = "Id_Cuenta"

        private const val TABLE_TRANSFERENCIA = "Transferencia"
        private const val COLUMN_ID_TRANSFERENCIA = "Id_Transferencia"
        private const val COLUMN_MONTO = "Monto"
        private const val COLUMN_TIPODES = "TipoMonedaDes"
        private const val COLUMN_CUENTA_DESTINO = "Cuenta_Destino"
        private const val COLUMN_FECHA = "Fecha"
        private const val COLUMN_HORA = "Hora"
        private const val COLUMN_ID_CUENTA_FK = "Id_Cuenta"

        private const val TABLE_REPORTE = "Reporte"
        private const val COLUMN_ID_REPORTE = "Id_Reporte"
        private const val COLUMN_TIPO_REPORTE = "Tipo_Reporte"
        private const val COLUMN_DESCRIPCION = "Descripcion"
        private const val COLUMN_RESPUESTA = "Respuesta"
        private const val COLUMN_ESTADO = "Estado"
        private const val COLUMN_FECHAR = "Fecha"
        private const val COLUMN_HORAR = "Hora"
        private const val COLUMN_ID_USER_FK = "Id_User"
    }

    override fun onCreate(db: SQLiteDatabase) {

        Log.d("DB", "onCreate")

        val createAdministradorTable = ("CREATE TABLE " + TABLE_ADMINISTRADOR+ "("
                + COLUMN_ID_ADMINISTRADOR + " TEXT PRIMARY KEY,"
                + COLUMN_NOMBRE_ADMIN + " TEXT,"
                + COLUMN_CONTRASEÑA_ADMIN + " TEXT" + ")")
        db.execSQL(createAdministradorTable)

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
                + COLUMN_RAZON + " TEXT,"
                + COLUMN_ID_USUARIO_FK + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_ID_USUARIO_FK + ") REFERENCES " + TABLE_USUARIO + "(" + COLUMN_ID_USUARIO + "))")
        db.execSQL(createCuentaTable)

        val createRecargaTable = ("CREATE TABLE " + TABLE_RECARGA + "("
                + COLUMN_ID_RECARGA + " INTEGER PRIMARY KEY,"
                + COLUMN_MONTO_RECARGA + " TEXT,"
                + COLUMN_FECHA_RECARGA + " DATE,"
                + COLUMN_HORA_RECARGA + " TIME,"
                + COLUMN_ID_CUENTA_RECARGA_FK + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_ID_CUENTA_RECARGA_FK + ") REFERENCES " + TABLE_USUARIO + "(" + COLUMN_ID_CUENTA + "))")
        db.execSQL(createRecargaTable)

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

        val createReporteTable = ("CREATE TABLE " + TABLE_REPORTE + "("
                + COLUMN_ID_REPORTE + " INTEGER PRIMARY KEY,"
                + COLUMN_TIPO_REPORTE + " TEXT,"
                + COLUMN_DESCRIPCION + " TEXT,"
                + COLUMN_RESPUESTA + " TEXT,"
                + COLUMN_ESTADO + " TEXT,"
                + COLUMN_FECHAR + " DATE,"
                + COLUMN_HORAR + " TIME,"
                + COLUMN_ID_USER_FK + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_ID_USER_FK + ") REFERENCES " + TABLE_USUARIO + "(" + COLUMN_ID_USUARIO + "))")
        db.execSQL(createReporteTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ADMINISTRADOR")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CUENTA")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_RECARGA")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSFERENCIA")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_REPORTE")
        onCreate(db)
    }

    fun checkIfAdmin(userName: String): Boolean {
        val db = readableDatabase
        val query = "SELECT COUNT(*) FROM $TABLE_ADMINISTRADOR WHERE $COLUMN_ID_ADMINISTRADOR = ?"
        val cursor = db.rawQuery(query, arrayOf(userName))

        var isAdmin = false
        if (cursor != null && cursor.moveToFirst()) {
            val count = cursor.getInt(0)
            isAdmin = count > 0
        }
        cursor.close()
        return isAdmin
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
        values.put(COLUMN_RAZON, "")
        values.put(COLUMN_ID_USUARIO_FK, idUsuario)
        db.insert(TABLE_CUENTA, null, values)
        db.close()
    }

    fun verifyAdmin(userName: String, password: String): Int {
        val db = readableDatabase
        val cursor = db.query(TABLE_ADMINISTRADOR, arrayOf(COLUMN_ID_ADMINISTRADOR),"$COLUMN_ID_ADMINISTRADOR=? AND $COLUMN_CONTRASEÑA_ADMIN=?",
            arrayOf(userName, password),
            null, null, null, null
        )

        if (cursor != null && cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(COLUMN_ID_ADMINISTRADOR)
            if (columnIndex != -1) {
                val adminid = cursor.getInt(columnIndex)
                cursor.close()
                return adminid
            }
        }
        cursor?.close()
        return -1
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
            Cuenta(idCuenta, saldo, tipoMoneda, "Activa", "")
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
        val query = "SELECT Id_Cuenta, Saldo, Tipo_Cuenta, Estado_Cuenta, Razon FROM $TABLE_CUENTA WHERE $COLUMN_ID_USUARIO_FK = ?"
        val cursor = db.rawQuery(query, arrayOf(idUsuario))

        if (cursor.moveToFirst()) {
            do {
                val idCuenta = cursor.getString(cursor.getColumnIndexOrThrow("Id_Cuenta"))
                val saldo = cursor.getDouble(cursor.getColumnIndexOrThrow("Saldo"))
                val tipoMoneda = cursor.getString(cursor.getColumnIndexOrThrow("Tipo_Cuenta"))
                val estadoCuenta = cursor.getString(cursor.getColumnIndexOrThrow("Estado_Cuenta"))
                val razon = cursor.getString(cursor.getColumnIndexOrThrow("Razon"))
                cuentasList.add(Cuenta(idCuenta, saldo, tipoMoneda, estadoCuenta, razon))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return cuentasList
    }

    fun getCuentasByUsuario2(idUsuario: String): MutableList<Cuentas> {
        val cuentasList = mutableListOf<Cuentas>()
        val db = this.readableDatabase
        val query = "SELECT Id_Cuenta, Saldo, Estado_Cuenta, Tipo_Cuenta FROM $TABLE_CUENTA WHERE $COLUMN_ID_USUARIO_FK = ?"
        val cursor = db.rawQuery(query, arrayOf(idUsuario))

        if (cursor.moveToFirst()) {
            do {
                val idCuenta = cursor.getString(cursor.getColumnIndexOrThrow("Id_Cuenta"))
                val saldo = cursor.getDouble(cursor.getColumnIndexOrThrow("Saldo"))
                val estadoCuenta = cursor.getString(cursor.getColumnIndexOrThrow("Estado_Cuenta"))
                val tipoMoneda = cursor.getString(cursor.getColumnIndexOrThrow("Tipo_Cuenta"))
                cuentasList.add(Cuentas(idCuenta, saldo,estadoCuenta, tipoMoneda))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return cuentasList
    }

    fun getTransferenciasByCuenta(idCuenta: String): MutableList<Transferencia> {
        val transferenciasList = mutableListOf<Transferencia>()
        val db = this.readableDatabase
        val query = """
        SELECT t.Monto, t.Fecha, t.Hora,
               cOrigen.$COLUMN_ID_CUENTA AS Cuenta_Origen, 
               uOrigen.$COLUMN_NOMBRE AS Nombre_Origen,
               cDestino.$COLUMN_ID_CUENTA AS Cuenta_Destino,
               uDestino.$COLUMN_NOMBRE AS Nombre_Destino
        FROM $TABLE_TRANSFERENCIA t
        LEFT JOIN $TABLE_CUENTA cOrigen ON t.$COLUMN_ID_CUENTA_FK = cOrigen.$COLUMN_ID_CUENTA
        LEFT JOIN $TABLE_USUARIO uOrigen ON cOrigen.$COLUMN_ID_USUARIO_FK = uOrigen.$COLUMN_ID_USUARIO
        LEFT JOIN $TABLE_CUENTA cDestino ON t.$COLUMN_CUENTA_DESTINO = cDestino.$COLUMN_ID_CUENTA
        LEFT JOIN $TABLE_USUARIO uDestino ON cDestino.$COLUMN_ID_USUARIO_FK = uDestino.$COLUMN_ID_USUARIO
        WHERE t.$COLUMN_ID_CUENTA_FK = ? OR t.$COLUMN_CUENTA_DESTINO = ? ORDER BY t.$COLUMN_FECHA DESC
    """
        val cursor = db.rawQuery(query, arrayOf(idCuenta, idCuenta))

        if (cursor.moveToFirst()) {
            do {
                val monto = cursor.getDouble(cursor.getColumnIndexOrThrow("Monto"))
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow("Fecha"))
                val hora = cursor.getString(cursor.getColumnIndexOrThrow("Hora"))
                val cuentaOrigen = cursor.getString(cursor.getColumnIndexOrThrow("Cuenta_Origen"))
                val nombreOrigen = cursor.getString(cursor.getColumnIndexOrThrow("Nombre_Origen"))
                val cuentaDestino = cursor.getString(cursor.getColumnIndexOrThrow("Cuenta_Destino"))
                val nombreDestino = cursor.getString(cursor.getColumnIndexOrThrow("Nombre_Destino"))


                val tipoTransferencia = if (cuentaOrigen == idCuenta) "Salida" else "Entrada"

                val transferencia = Transferencia(
                    monto,
                    fecha,
                    hora,
                    cuentaOrigen,
                    nombreOrigen,
                    cuentaDestino,
                    nombreDestino,
                    tipoTransferencia
                )
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

    fun getSaldoByCuenta(idCuenta: String): Double? {
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_SALDO FROM $TABLE_CUENTA WHERE $COLUMN_ID_CUENTA = ?"
        val cursor = db.rawQuery(query, arrayOf(idCuenta))

        return if (cursor.moveToFirst()) {
            val saldo = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_SALDO))
            cursor.close()
            saldo
        } else {
            cursor.close()
            null
        }
    }

    fun updateSaldoByIdCuenta(idCuenta: String, nuevoSaldo: Double) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_SALDO, nuevoSaldo)

        db.update(TABLE_CUENTA, values, "$COLUMN_ID_CUENTA = ?", arrayOf(idCuenta))
        db.close()
    }

    fun addReporte(
        idReporte: Int,
        tipoReporte: String,
        descripcion: String,
        repuesta: String,
        estado:String,
        fecha: String,
        hora: String,
        idUsuarioFk: String
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID_REPORTE, idReporte)
        values.put(COLUMN_TIPO_REPORTE, tipoReporte)
        values.put(COLUMN_DESCRIPCION, descripcion)
        values.put(COLUMN_RESPUESTA, repuesta)
        values.put(COLUMN_ESTADO, estado)
        values.put(COLUMN_FECHAR, fecha)
        values.put(COLUMN_HORAR, hora)
        values.put(COLUMN_ID_USER_FK, idUsuarioFk)

        db.insert(TABLE_REPORTE, null, values)
        db.close()
    }

    fun getReportesByUsuario(idUsuario: String): MutableList<Reporte> {
        val reportesList = mutableListOf<Reporte>()
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_ID_REPORTE, $COLUMN_TIPO_REPORTE, $COLUMN_DESCRIPCION, $COLUMN_RESPUESTA, $COLUMN_ESTADO, $COLUMN_FECHAR || ' ' || $COLUMN_HORAR AS FechayHora " +
                "FROM $TABLE_REPORTE " +
                "WHERE $COLUMN_ID_USER_FK = ? " +
                "ORDER BY $COLUMN_FECHAR DESC, $COLUMN_HORAR DESC"
        val cursor = db.rawQuery(query, arrayOf(idUsuario))

        if (cursor.moveToFirst()) {
            do {
                val idReporte = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_REPORTE))
                val tipoReporte = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPO_REPORTE))
                val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION))
                val respuesta = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESPUESTA))
                val estado = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ESTADO))
                val fechayHora = cursor.getString(cursor.getColumnIndexOrThrow("FechayHora"))

                val reporte = Reporte(idReporte.toString(), tipoReporte, descripcion, respuesta, estado, fechayHora)
                reportesList.add(reporte)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return reportesList
    }

    fun getAccountById(idCuenta: String): Cuenta? {
        val db = readableDatabase
        val query = "SELECT Id_Cuenta, Saldo, Tipo_Cuenta FROM Cuenta WHERE Id_Cuenta = ?"
        val cursor = db.rawQuery(query, arrayOf(idCuenta))

        return if (cursor.moveToFirst()) {
            val idCuenta = cursor.getString(cursor.getColumnIndexOrThrow("Id_Cuenta"))
            val saldo = cursor.getDouble(cursor.getColumnIndexOrThrow("Saldo"))
            val tipoMoneda = cursor.getString(cursor.getColumnIndexOrThrow("Tipo_Cuenta"))
            cursor.close()
            Cuenta(idCuenta, saldo, tipoMoneda, "Activa", "")
        } else {
            cursor.close()
            null
        }
    }
    fun recargarCuenta(idCuenta: String, monto: Double) {
        val db = writableDatabase
        val query = "UPDATE Cuenta SET Saldo = Saldo + ? WHERE Id_Cuenta = ?"
        val statement = db.compileStatement(query)
        statement.bindDouble(1, monto)
        statement.bindString(2, idCuenta)
        statement.executeUpdateDelete()
    }

    fun createDefaultAdministradores() {
        val db = this.writableDatabase

        try {
            val admin1Values = ContentValues().apply {
                put(COLUMN_ID_ADMINISTRADOR,"7777777")
                put(COLUMN_NOMBRE_ADMIN, "Admin1")
                put(COLUMN_CONTRASEÑA_ADMIN, "admin123")
            }
            db.insert(TABLE_ADMINISTRADOR, null, admin1Values)

            val admin2Values = ContentValues().apply {
                put(COLUMN_ID_ADMINISTRADOR,"6666666")
                put(COLUMN_NOMBRE_ADMIN, "Admin2")
                put(COLUMN_CONTRASEÑA_ADMIN, "admin123")
            }
            db.insert(TABLE_ADMINISTRADOR, null, admin2Values)

            Log.d("ADMIN", "Se han creado dos administradores correctamente.")
        } catch (e: Exception) {
            Log.e("ADMIN", "Error al crear administradores: ${e.message}")
        } finally {
            db.close()
        }
    }


    fun getAllUsuarios(): MutableList<Usuarios> {
        val usuariosList = mutableListOf<Usuarios>()
        val db = readableDatabase
        val query = "SELECT $COLUMN_ID_USUARIO, $COLUMN_NOMBRE FROM $TABLE_USUARIO"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val idUser = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID_USUARIO))
                val nameUser = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
                val usuario = Usuarios(nameUser, idUser)
                usuariosList.add(usuario)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return usuariosList
    }


    fun getAllReportes(): MutableList<Reportes> {
        val reportesList = mutableListOf<Reportes>()
        val db = readableDatabase
        val query = """SELECT $COLUMN_ID_REPORTE, $COLUMN_TIPO_REPORTE,$COLUMN_DESCRIPCION, $COLUMN_RESPUESTA,
                                $COLUMN_ESTADO, $COLUMN_FECHAR, $COLUMN_HORAR
                        FROM $TABLE_REPORTE ORDER BY $COLUMN_FECHAR DESC, $COLUMN_HORAR DESC"""
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val idReporte = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_REPORTE))
                val tipoReporte = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPO_REPORTE))
                val respuesta = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESPUESTA))
                val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION))
                val estado = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ESTADO))
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHAR))
                val hora = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HORAR))

                val reporte = Reportes(idReporte.toString(), tipoReporte, descripcion, respuesta, estado, fecha, hora)
                reportesList.add(reporte)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return reportesList
    }

    fun getCurrencyTypeById(idCuenta: String): String? {
        val db = readableDatabase
        val query = "SELECT Tipo_Cuenta FROM Cuenta WHERE Id_Cuenta = ?"
        val cursor = db.rawQuery(query, arrayOf(idCuenta))

        return if (cursor.moveToFirst()) {
            val tipoMoneda = cursor.getString(cursor.getColumnIndexOrThrow("Tipo_Cuenta"))
            cursor.close()
            tipoMoneda
        } else {
            cursor.close()
            null
        }
    }

    fun getAdminNameById(adminId: String): String? {
        val db = this.readableDatabase
        var name: String? = null

        val query = "SELECT $COLUMN_NOMBRE_ADMIN FROM $TABLE_ADMINISTRADOR WHERE $COLUMN_ID_ADMINISTRADOR = ?"
        val cursor = db.rawQuery(query, arrayOf(adminId))

        if (cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE_ADMIN))
        }
        cursor.close()
        return name
    }


    fun actualizarReporte(id: String, nuevoEstado: String, nuevaRespuesta: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_ESTADO, nuevoEstado)
        contentValues.put(COLUMN_RESPUESTA, nuevaRespuesta)

        val resultado = db.update(TABLE_REPORTE, contentValues, "$COLUMN_ID_REPORTE = ?", arrayOf(id))
        db.close()
        return resultado > 0
    }

    fun cambiarEstadoCuenta(idCuenta: String, nuevoEstado: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_ESTADO_CUENTA, nuevoEstado)

        val resultado = db.update(TABLE_CUENTA, contentValues, "$COLUMN_ID_CUENTA = ?", arrayOf(idCuenta))
        db.close()
        return resultado > 0
    }

    fun updateRazonCuenta(idCuenta: String, updateRazon: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_RAZON, updateRazon)

        val resultado = db.update(TABLE_CUENTA, contentValues, "$COLUMN_ID_CUENTA = ?", arrayOf(idCuenta))
        db.close()
        return resultado > 0
    }

    fun getEstadoCuentaById(idCuenta: String): String? {
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_ESTADO_CUENTA FROM $TABLE_CUENTA WHERE $COLUMN_ID_CUENTA = ?"
        val cursor = db.rawQuery(query, arrayOf(idCuenta))

        return if (cursor.moveToFirst()) {
            val estadoCuenta = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ESTADO_CUENTA))
            cursor.close()
            estadoCuenta
        } else {
            cursor.close()
            null
        }
    }
    fun addRecarga(idRecarga: Int, monto: String, fecha: String, hora: String, idCuenta: Int): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COLUMN_ID_RECARGA, idRecarga)
        contentValues.put(COLUMN_MONTO_RECARGA, monto)
        contentValues.put(COLUMN_FECHA_RECARGA, fecha)
        contentValues.put(COLUMN_HORA_RECARGA, hora)
        contentValues.put(COLUMN_ID_CUENTA_RECARGA_FK, idCuenta)

        val result = db.insert(TABLE_RECARGA, null, contentValues)
        db.close()
        return result
    }

    fun getRecargasByIdCuenta(idCuenta: String): MutableList<Recarga> {
        val recargas = mutableListOf<Recarga>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_RECARGA WHERE $COLUMN_ID_CUENTA_RECARGA_FK = ?"
        val cursor = db.rawQuery(query, arrayOf(idCuenta))

        if (cursor.moveToFirst()) {
            do {
                val idRecarga = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_RECARGA))
                val monto = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONTO_RECARGA))
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA_RECARGA))
                val hora = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HORA_RECARGA))
                val idCuentaFk = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID_CUENTA_RECARGA_FK))

                val recarga = Recarga(idRecarga, monto, fecha, hora, idCuentaFk)
                recargas.add(recarga)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return recargas
    }



}
