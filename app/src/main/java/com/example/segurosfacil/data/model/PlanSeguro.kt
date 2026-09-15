package com.example.segurosfacil.data.model

data class PlanSeguro(
    val id: String,
    val tipo: TipoSeguro,
    val aseguradora: String,
    val nombrePlan: String,
    val cobertura: String,
    val primaMensual: Double,
    val deducible: Double? = null
)