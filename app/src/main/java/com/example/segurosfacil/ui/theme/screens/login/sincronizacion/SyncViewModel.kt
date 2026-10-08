package com.example.segurosfacil.ui.screens.sincronizacion

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.segurosfacil.data.repository.AppDatabase
import com.example.segurosfacil.data.repository.SyncRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SyncViewModel(application: Application) : AndroidViewModel(application) {

    private val operacionDao = AppDatabase.getDatabase(application).operacionPendienteDao()
    private val syncMetadataDao = AppDatabase.getDatabase(application).syncMetadataDao()
    private val syncRepository = SyncRepository(application)

    private val _pendientes = MutableStateFlow(0)
    val pendientes: StateFlow<Int> = _pendientes

    private val _errores = MutableStateFlow(0)
    val errores: StateFlow<Int> = _errores

    private val _ultimaSincronizacion = MutableStateFlow<Long?>(null)

    private val _sincronizando = MutableStateFlow(false)
    val sincronizando: StateFlow<Boolean> = _sincronizando

    private val _ultimoResultado = MutableStateFlow<String?>(null)
    val ultimoResultado: StateFlow<String?> = _ultimoResultado

    fun refrescarEstado() {
        viewModelScope.launch {
            _pendientes.value = operacionDao.contarPendientes()
            _errores.value = operacionDao.contarErrores()
            val metaCotizaciones = syncMetadataDao.obtener("cotizaciones")?.ultimaSincronizacion
            val metaPolizas = syncMetadataDao.obtener("polizas")?.ultimaSincronizacion
            _ultimaSincronizacion.value = listOfNotNull(metaCotizaciones, metaPolizas).maxOrNull()
        }
    }

    fun sincronizarAhora() {
        viewModelScope.launch {
            _sincronizando.value = true
            val (exitosos, fallidos) = syncRepository.procesarPendientes()
            _ultimoResultado.value = "Sincronizados: $exitosos, con error: $fallidos"
            refrescarEstado()
            _sincronizando.value = false
        }
    }

    fun obtenerUltimaSincronizacionTexto(): String {
        val valor = _ultimaSincronizacion.value ?: return "Nunca"
        return java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
            .format(java.util.Date(valor))
    }
}