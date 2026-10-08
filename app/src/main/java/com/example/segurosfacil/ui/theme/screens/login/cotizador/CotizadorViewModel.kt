package com.example.segurosfacil.ui.screens.cotizador

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.segurosfacil.data.model.CotizacionEntity
import com.example.segurosfacil.data.model.PlanSeguro
import com.example.segurosfacil.data.model.TipoSeguro
import com.example.segurosfacil.data.repository.AppDatabase
import com.example.segurosfacil.data.repository.PlanRepository
import com.example.segurosfacil.data.repository.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.example.segurosfacil.data.repository.SyncRepository
import com.example.segurosfacil.data.repository.toIso8601

class CotizadorViewModel(application: Application) : AndroidViewModel(application) {

    private val cotizacionDao = AppDatabase.getDatabase(application).cotizacionDao()

    private val _planCotizado = MutableStateFlow<PlanSeguro?>(null)
    val planCotizado: StateFlow<PlanSeguro?> = _planCotizado

    private val _primaEstimada = MutableStateFlow<Double?>(null)
    val primaEstimada: StateFlow<Double?> = _primaEstimada

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    private val _registroExitoso = MutableStateFlow(false)
    val registroExitoso: StateFlow<Boolean> = _registroExitoso

    fun obtenerCoberturasPorTipo(tipo: TipoSeguro): List<String> {
        return PlanRepository.planes.filter { it.tipo == tipo }.map { it.cobertura }.distinct()
    }

    private fun calcularFactorRiesgo(edad: Int): Double {
        return when {
            edad < 25 -> 1.3
            edad <= 50 -> 1.0
            else -> 1.15
        }
    }

    fun cotizar(tipo: TipoSeguro?, cobertura: String?, edad: Int?) {
        if (tipo == null || cobertura.isNullOrBlank()) {
            _mensaje.value = "Debes seleccionar tipo de seguro y cobertura"
            return
        }
        if (edad == null || edad <= 0) {
            _mensaje.value = "Ingresa una edad válida"
            return
        }

        val plan = PlanRepository.planes.find { it.tipo == tipo && it.cobertura == cobertura }
        if (plan == null) {
            _mensaje.value = "No se encontró un plan con esa combinación"
            return
        }

        val factorRiesgo = calcularFactorRiesgo(edad)
        _planCotizado.value = plan
        _primaEstimada.value = plan.primaMensual * factorRiesgo
        _mensaje.value = null
    }

    fun registrarCotizacion() {
        val plan = _planCotizado.value ?: return
        val prima = _primaEstimada.value ?: return

        viewModelScope.launch {
            val usuarioId = SessionManager.obtenerUsuarioId(getApplication()).first() ?: return@launch
            val fecha = System.currentTimeMillis()

            cotizacionDao.insertar(
                CotizacionEntity(
                    usuarioId = usuarioId,
                    planId = plan.id.toInt(),
                    primaEstimada = prima,
                    fecha = fecha
                )
            )

            SyncRepository(getApplication()).encolarCotizacion(
                usuarioId = usuarioId,
                planId = plan.id.toInt(),
                primaEstimada = prima,
                fechaIso = fecha.toIso8601()
            )

            _registroExitoso.value = true
        }
    }
    fun resetear() {
        _planCotizado.value = null
        _primaEstimada.value = null
        _registroExitoso.value = false
        _mensaje.value = null
    }
}