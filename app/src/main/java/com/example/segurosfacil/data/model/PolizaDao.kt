package com.example.segurosfacil.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PolizaDao {

    @Insert
    suspend fun insertar(poliza: PolizaContratadaEntity)

    @Query("SELECT * FROM polizas_contratadas WHERE usuarioId = :usuarioId")
    suspend fun obtenerPorUsuario(usuarioId: Int): List<PolizaContratadaEntity>
}