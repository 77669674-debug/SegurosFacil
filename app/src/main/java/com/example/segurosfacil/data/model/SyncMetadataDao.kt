package com.example.segurosfacil.data.model

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface SyncMetadataDao {

    @Upsert
    suspend fun actualizar(metadata: SyncMetadataEntity)

    @Query("SELECT * FROM sync_metadata WHERE entidad = :entidad")
    suspend fun obtener(entidad: String): SyncMetadataEntity?
}