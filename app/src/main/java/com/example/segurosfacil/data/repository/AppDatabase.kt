package com.example.segurosfacil.data.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.segurosfacil.data.model.PolizaContratadaEntity
import com.example.segurosfacil.data.model.PolizaDao
import com.example.segurosfacil.data.model.SiniestroDao
import com.example.segurosfacil.data.model.SiniestroEntity
import com.example.segurosfacil.data.model.UsuarioDao
import com.example.segurosfacil.data.model.UsuarioEntity
import com.example.segurosfacil.data.model.PlanSeguroEntity
import com.example.segurosfacil.data.model.PlanSeguroDao
import com.example.segurosfacil.data.model.CotizacionEntity
import com.example.segurosfacil.data.model.CotizacionDao
import com.example.segurosfacil.data.model.OperacionPendienteEntity
import com.example.segurosfacil.data.model.OperacionPendienteDao
import com.example.segurosfacil.data.model.SyncMetadataEntity
import com.example.segurosfacil.data.model.SyncMetadataDao


@Database(
    entities = [UsuarioEntity::class, PolizaContratadaEntity::class, SiniestroEntity::class, CotizacionEntity::class, PlanSeguroEntity::class, OperacionPendienteEntity::class, SyncMetadataEntity::class],
    version = 5
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun polizaDao(): PolizaDao
    abstract fun siniestroDao(): SiniestroDao
    abstract fun cotizacionDao(): CotizacionDao
    abstract fun planSeguroDao(): PlanSeguroDao

    abstract fun operacionPendienteDao(): OperacionPendienteDao

    abstract fun syncMetadataDao(): SyncMetadataDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "segurofacil_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instancia
                instancia
            }
        }
    }
}