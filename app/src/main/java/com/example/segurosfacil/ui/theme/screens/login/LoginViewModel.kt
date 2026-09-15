package com.example.segurosfacil.ui.screens.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.segurosfacil.data.model.UsuarioEntity
import com.example.segurosfacil.data.repository.AppDatabase
import com.example.segurosfacil.data.repository.encriptarPassword
import com.example.segurosfacil.data.repository.verificarPassword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.segurosfacil.data.repository.SessionManager

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    private val _loginExitoso = MutableStateFlow(false)
    val loginExitoso: StateFlow<Boolean> = _loginExitoso

    fun registrar(nombre: String, correo: String, password: String) {
        viewModelScope.launch {
            val existente = usuarioDao.buscarPorCorreo(correo)
            if (existente != null) {
                _mensaje.value = "Ya existe una cuenta con ese correo"
                return@launch
            }
            val nuevoUsuario = UsuarioEntity(
                nombre = nombre,
                correo = correo,
                passwordHash = encriptarPassword(password)
            )
            usuarioDao.insertar(nuevoUsuario)
            _mensaje.value = "Cuenta creada, ya puedes iniciar sesión"
        }
    }

    fun login(correo: String, password: String) {
        viewModelScope.launch {
            val usuario = usuarioDao.buscarPorCorreo(correo)
            if (usuario == null) {
                _mensaje.value = "No existe una cuenta con ese correo"
                return@launch
            }
            val passwordCorrecta = verificarPassword(password, usuario.passwordHash)
            if (passwordCorrecta) {
                SessionManager.guardarSesion(getApplication(), usuario.id)
                _loginExitoso.value = true
            } else {
                _mensaje.value = "Contraseña incorrecta"
            }
        }
    }
}