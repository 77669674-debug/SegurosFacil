package com.example.segurosfacil.data.remote.api

import com.example.segurosfacil.data.remote.dto.CotizacionDto
import com.example.segurosfacil.data.remote.dto.PlanSeguroDto
import com.example.segurosfacil.data.remote.dto.PolizaDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("planes_seguro")
    suspend fun obtenerPlanes(): List<PlanSeguroDto>

    @POST("cotizaciones")
    suspend fun crearCotizacion(@Body dto: CotizacionDto): List<CotizacionDto>

    @POST("polizas")
    suspend fun crearPoliza(@Body dto: PolizaDto): List<PolizaDto>
}