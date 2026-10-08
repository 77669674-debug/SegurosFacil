package com.example.segurosfacil.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlanSeguroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(planes: List<PlanSeguroEntity>)

    @Query("SELECT * FROM planes_seguro_cache")
    suspend fun obtenerTodos(): List<PlanSeguroEntity>
}