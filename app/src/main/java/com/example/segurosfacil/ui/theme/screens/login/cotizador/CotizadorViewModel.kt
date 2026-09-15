package com.example.segurosfacil.ui.screens.cotizador

import androidx.lifecycle.ViewModel
import com.example.segurosfacil.data.model.PlanSeguro
import com.example.segurosfacil.data.model.TipoSeguro
import com.example.segurosfacil.data.repository.PlanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CotizadorViewModel : ViewModel() {

    private val _planSugerido = MutableStateFlow<PlanSeguro?>(null)
    val planSugerido: StateFlow<PlanSeguro?> = _planSugerido

    private val _sinResultados = MutableStateFlow(false)
    val sinResultados: StateFlow<Boolean> = _sinResultados

    fun cotizar(tipo: TipoSeguro, presupuestoMaximo: Double) {
        val opciones = PlanRepository.planes
            .filter { it.tipo == tipo && it.primaMensual <= presupuestoMaximo }
            .sortedByDescending { it.primaMensual }

        if (opciones.isEmpty()) {
            _sinResultados.value = true
            _planSugerido.value = null
        } else {
            _sinResultados.value = false
            _planSugerido.value = opciones.first()
        }
    }
}