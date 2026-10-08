package com.example.segurosfacil.data.repository

import android.content.Context
import com.example.segurosfacil.data.mapper.toDomain
import com.example.segurosfacil.data.mapper.toEntity
import com.example.segurosfacil.data.model.PlanSeguro
import com.example.segurosfacil.data.model.PlanSeguroDao
import com.example.segurosfacil.data.remote.RetrofitClient
import com.example.segurosfacil.data.remote.api.ApiService

object PlanRepository {

    private lateinit var planDao: PlanSeguroDao

    var planes: List<PlanSeguro> = emptyList()
        private set

    fun init(context: Context) {
        planDao = AppDatabase.getDatabase(context).planSeguroDao()
    }

    suspend fun cargarDesdeRoom() {
        planes = planDao.obtenerTodos().map { it.toDomain() }
    }

    suspend fun sincronizarDesdeApi() {
        val api = RetrofitClient.instance.create(ApiService::class.java)
        val dtos = api.obtenerPlanes()
        planDao.insertarTodos(dtos.map { it.toEntity() })
        cargarDesdeRoom()
    }
}