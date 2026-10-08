package com.example.segurosfacil.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PlanSeguroDto(
    val id: Int = 0,
    val nombre: String,
    @SerializedName("tipoSeguro") val tipoSeguro: String,
    val aseguradora: String,
    val cobertura: String,
    @SerializedName("primaBase") val primaBase: Double
)