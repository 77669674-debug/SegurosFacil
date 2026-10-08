package com.example.segurosfacil.data.repository

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object SyncScheduler {

    fun programarSincronizacionAutomatica(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "sync_periodico",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    fun sincronizarAhora(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueue(request)
    }
}