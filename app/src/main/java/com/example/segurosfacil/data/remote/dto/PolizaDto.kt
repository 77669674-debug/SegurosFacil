package com.example.segurosfacil.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PolizaDto(
    val id: Int? = null,
    @SerializedName("usuarioId") val usuarioId: Int,
    @SerializedName("planId") val planId: Int,
    @SerializedName("cotizacionId") val cotizacionId: Int? = null,
    val estado: String,
    @SerializedName("uuid_operacion") val uuidOperacion: String
)