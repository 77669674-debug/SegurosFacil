package com.example.segurosfacil.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CotizacionDto(
    val id: Int? = null,
    @SerializedName("usuarioId") val usuarioId: Int,
    @SerializedName("planId") val planId: Int,
    @SerializedName("primaEstimada") val primaEstimada: Double,
    val fecha: String,
    @SerializedName("uuid_operacion") val uuidOperacion: String
)