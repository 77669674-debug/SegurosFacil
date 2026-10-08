package com.example.segurosfacil.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CotizacionDao {

    @Insert
    suspend fun insertar(cotizacion: CotizacionEntity): Long

    @Query("SELECT * FROM cotizaciones_local WHERE usuarioId = :usuarioId")
    suspend fun obtenerPorUsuario(usuarioId: Int): List<CotizacionEntity>
}