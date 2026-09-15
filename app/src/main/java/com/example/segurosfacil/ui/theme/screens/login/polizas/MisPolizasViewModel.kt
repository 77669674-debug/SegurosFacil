package com.example.segurosfacil.ui.screens.polizas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.segurosfacil.data.model.PlanSeguro
import com.example.segurosfacil.data.repository.AppDatabase
import com.example.segurosfacil.data.repository.PlanRepository
import com.example.segurosfacil.data.repository.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MisPolizasViewModel(application: Application) : AndroidViewModel(application) {

    private val polizaDao = AppDatabase.getDatabase(application).polizaDao()

    private val _planesContratados = MutableStateFlow<List<PlanSeguro>>(emptyList())
    val planesContratados: StateFlow<List<PlanSeguro>> = _planesContratados

    fun cargarPolizas() {
        viewModelScope.launch {
            val usuarioId = SessionManager.obtenerUsuarioId(getApplication()).first()
            if (usuarioId == null) return@launch

            val polizas = polizaDao.obtenerPorUsuario(usuarioId)
            _planesContratados.value = polizas.mapNotNull { poliza ->
                PlanRepository.planes.find { it.id == poliza.planId }
            }
        }
    }
}