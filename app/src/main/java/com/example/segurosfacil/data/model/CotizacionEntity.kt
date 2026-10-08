package com.example.segurosfacil.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cotizaciones_local")
data class CotizacionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val usuarioId: Int,
    val planId: Int,
    val primaEstimada: Double,
    val fecha: Long
)