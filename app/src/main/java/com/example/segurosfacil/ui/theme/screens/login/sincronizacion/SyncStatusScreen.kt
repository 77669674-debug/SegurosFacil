package com.example.segurosfacil.ui.screens.sincronizacion

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SyncStatusScreen(
    viewModel: SyncViewModel = viewModel()
) {
    val pendientes by viewModel.pendientes.collectAsState()
    val errores by viewModel.errores.collectAsState()
    val sincronizando by viewModel.sincronizando.collectAsState()
    val ultimoResultado by viewModel.ultimoResultado.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refrescarEstado()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sincronización", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(24.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Operaciones pendientes: $pendientes")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Operaciones con error: $errores")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Última sincronización: ${viewModel.obtenerUltimaSincronizacionTexto()}")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (sincronizando) {
            CircularProgressIndicator()
        } else {
            Button(onClick = { viewModel.sincronizarAhora() }, modifier = Modifier.fillMaxWidth()) {
                Text("Sincronizar ahora")
            }
        }

        ultimoResultado?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = it)
        }
    }
}