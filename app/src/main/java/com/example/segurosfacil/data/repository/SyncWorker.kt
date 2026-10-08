package com.example.segurosfacil.data.repository

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class SyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val syncRepository = SyncRepository(applicationContext)
            syncRepository.procesarPendientes()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}