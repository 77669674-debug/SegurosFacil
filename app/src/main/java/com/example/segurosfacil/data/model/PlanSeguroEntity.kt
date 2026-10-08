package com.example.segurosfacil.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planes_seguro_cache")
data class PlanSeguroEntity(
    @PrimaryKey val id: Int,
    val nombre: String,
    val tipoSeguro: String,
    val aseguradora: String,
    val cobertura: String,
    val primaBase: Double
)