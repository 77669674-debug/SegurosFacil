package com.example.segurosfacil.ui.screens.siniestro

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.segurosfacil.data.model.SiniestroEntity
import com.example.segurosfacil.data.repository.AppDatabase
import com.example.segurosfacil.data.repository.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SiniestroViewModel(application: Application) : AndroidViewModel(application) {

    private val siniestroDao = AppDatabase.getDatabase(application).siniestroDao()

    private val _siniestros = MutableStateFlow<List<SiniestroEntity>>(emptyList())
    val siniestros: StateFlow<List<SiniestroEntity>> = _siniestros

    private val _reporteExitoso = MutableStateFlow(false)
    val reporteExitoso: StateFlow<Boolean> = _reporteExitoso

    fun cargarSiniestros() {
        viewModelScope.launch {
            val usuarioId = SessionManager.obtenerUsuarioId(getApplication()).first() ?: return@launch
            _siniestros.value = siniestroDao.obtenerPorUsuario(usuarioId)
        }
    }

    fun reportar(tipoSiniestro: String, descripcion: String) {
        viewModelScope.launch {
            val usuarioId = SessionManager.obtenerUsuarioId(getApplication()).first() ?: return@launch

            siniestroDao.insertar(
                SiniestroEntity(
                    usuarioId = usuarioId,
                    tipoSiniestro = tipoSiniestro,
                    descripcion = descripcion,
                    fecha = System.currentTimeMillis()
                )
            )
            _reporteExitoso.value = true
            cargarSiniestros()
        }
    }

    fun resetearReporte() {
        _reporteExitoso.value = false
    }
}