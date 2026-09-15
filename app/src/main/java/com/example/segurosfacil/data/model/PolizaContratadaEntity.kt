package com.example.segurosfacil.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "polizas_contratadas")
data class PolizaContratadaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val usuarioId: Int,
    val planId: String,
    val fechaContratacion: Long
)