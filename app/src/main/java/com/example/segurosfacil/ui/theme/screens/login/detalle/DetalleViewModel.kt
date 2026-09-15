package com.example.segurosfacil.ui.screens.detalle

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.segurosfacil.data.model.PolizaContratadaEntity
import com.example.segurosfacil.data.repository.AppDatabase
import com.example.segurosfacil.data.repository.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetalleViewModel(application: Application) : AndroidViewModel(application) {

    private val polizaDao = AppDatabase.getDatabase(application).polizaDao()

    private val _contratacionExitosa = MutableStateFlow(false)
    val contratacionExitosa: StateFlow<Boolean> = _contratacionExitosa

    fun contratar(planId: String) {
        viewModelScope.launch {
            val usuarioId = SessionManager.obtenerUsuarioId(getApplication()).first()
            if (usuarioId == null) return@launch

            polizaDao.insertar(
                PolizaContratadaEntity(
                    usuarioId = usuarioId,
                    planId = planId,
                    fechaContratacion = System.currentTimeMillis()
                )
            )
            _contratacionExitosa.value = true
        }
    }
}