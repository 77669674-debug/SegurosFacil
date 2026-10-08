package com.example.segurosfacil.data.repository

import android.content.Context
import com.example.segurosfacil.data.model.OperacionPendienteEntity
import com.example.segurosfacil.data.model.SyncMetadataEntity
import com.example.segurosfacil.data.remote.RetrofitClient
import com.example.segurosfacil.data.remote.api.ApiService
import com.example.segurosfacil.data.remote.dto.CotizacionDto
import com.example.segurosfacil.data.remote.dto.PolizaDto
import com.google.gson.Gson
import retrofit2.HttpException
import java.util.UUID

class SyncRepository(context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val operacionDao = db.operacionPendienteDao()
    private val syncMetadataDao = db.syncMetadataDao()
    private val api = RetrofitClient.instance.create(ApiService::class.java)
    private val gson = Gson()

    suspend fun encolarCotizacion(usuarioId: Int, planId: Int, primaEstimada: Double, fechaIso: String) {
        val uuid = UUID.randomUUID().toString()
        val dto = CotizacionDto(
            usuarioId = usuarioId,
            planId = planId,
            primaEstimada = primaEstimada,
            fecha = fechaIso,
            uuidOperacion = uuid
        )
        operacionDao.insertar(
            OperacionPendienteEntity(
                uuidOperacion = uuid,
                entidad = "cotizaciones",
                payload = gson.toJson(dto),
                fechaCreacion = System.currentTimeMillis()
            )
        )
    }

    suspend fun encolarPoliza(usuarioId: Int, planId: Int, estado: String) {
        val uuid = UUID.randomUUID().toString()
        val dto = PolizaDto(
            usuarioId = usuarioId,
            planId = planId,
            estado = estado,
            uuidOperacion = uuid
        )
        operacionDao.insertar(
            OperacionPendienteEntity(
                uuidOperacion = uuid,
                entidad = "polizas",
                payload = gson.toJson(dto),
                fechaCreacion = System.currentTimeMillis()
            )
        )
    }

    suspend fun procesarPendientes(): Pair<Int, Int> {
        val pendientes = operacionDao.obtenerPendientes()
        var exitosos = 0
        var fallidos = 0

        for (operacion in pendientes) {
            operacionDao.actualizarEstado(operacion.id, "ENVIANDO", null)
            try {
                when (operacion.entidad) {
                    "cotizaciones" -> {
                        val dto = gson.fromJson(operacion.payload, CotizacionDto::class.java)
                        api.crearCotizacion(dto)
                    }
                    "polizas" -> {
                        val dto = gson.fromJson(operacion.payload, PolizaDto::class.java)
                        api.crearPoliza(dto)
                    }
                }
                operacionDao.actualizarEstado(operacion.id, "SINCRONIZADO", null)
                syncMetadataDao.actualizar(SyncMetadataEntity(operacion.entidad, System.currentTimeMillis()))
                exitosos++
            } catch (e: HttpException) {
                if (e.code() == 409) {
                    operacionDao.actualizarEstado(operacion.id, "SINCRONIZADO", "Ya existía (duplicado evitado)")
                    exitosos++
                } else {
                    operacionDao.actualizarEstado(operacion.id, "ERROR", "HTTP ${e.code()}: ${e.message()}")
                    fallidos++
                }
            } catch (e: Exception) {
                operacionDao.actualizarEstado(operacion.id, "ERROR", e.message ?: "Error desconocido")
                fallidos++
            }
        }
        return Pair(exitosos, fallidos)
    }
}