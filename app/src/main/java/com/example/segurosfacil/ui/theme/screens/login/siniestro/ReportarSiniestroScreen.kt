package com.example.segurosfacil.ui.screens.siniestro

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ReportarSiniestroScreen(
    viewModel: SiniestroViewModel = viewModel()
) {
    var tipoSiniestro by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    val siniestros by viewModel.siniestros.collectAsState()
    val reporteExitoso by viewModel.reporteExitoso.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarSiniestros()
    }

    LaunchedEffect(reporteExitoso) {
        if (reporteExitoso) {
            tipoSiniestro = ""
            descripcion = ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Reportar siniestro", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = tipoSiniestro,
            onValueChange = { tipoSiniestro = it },
            label = { Text("Tipo de siniestro (ej. Choque, Robo)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth().height(120.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (reporteExitoso) {
            Text(text = "Siniestro reportado correctamente")
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                viewModel.reportar(tipoSiniestro, descripcion)
                viewModel.resetearReporte()
            },
            enabled = tipoSiniestro.isNotBlank() && descripcion.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reportar")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Mis siniestros reportados", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        if (siniestros.isEmpty()) {
            Text("No has reportado ningún siniestro todavía")
        } else {
            siniestros.forEach { siniestro ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = siniestro.tipoSiniestro, style = MaterialTheme.typography.titleSmall)
                        Text(text = siniestro.descripcion, style = MaterialTheme.typography.bodySmall)
                        Text(
                            text = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                                .format(Date(siniestro.fecha)),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}