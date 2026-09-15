package com.example.segurosfacil.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SiniestroDao {

    @Insert
    suspend fun insertar(siniestro: SiniestroEntity)

    @Query("SELECT * FROM siniestros WHERE usuarioId = :usuarioId ORDER BY fecha DESC")
    suspend fun obtenerPorUsuario(usuarioId: Int): List<SiniestroEntity>
}