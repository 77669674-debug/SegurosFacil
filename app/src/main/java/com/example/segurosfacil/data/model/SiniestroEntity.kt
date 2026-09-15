package com.example.segurosfacil.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "siniestros")
data class SiniestroEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val usuarioId: Int,
    val tipoSiniestro: String,
    val descripcion: String,
    val fecha: Long
)