package com.example.segurosfacil.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "sesion")

object SessionManager {
    private val USUARIO_ID_KEY = intPreferencesKey("usuario_id")

    suspend fun guardarSesion(context: Context, usuarioId: Int) {
        context.dataStore.edit { prefs ->
            prefs[USUARIO_ID_KEY] = usuarioId
        }
    }

    fun obtenerUsuarioId(context: Context): Flow<Int?> {
        return context.dataStore.data.map { prefs -> prefs[USUARIO_ID_KEY] }
    }

    suspend fun cerrarSesion(context: Context) {
        context.dataStore.edit { prefs -> prefs.remove(USUARIO_ID_KEY) }
    }
}