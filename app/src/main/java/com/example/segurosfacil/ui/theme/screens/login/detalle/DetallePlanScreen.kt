package com.example.segurosfacil.ui.screens.detalle

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.segurosfacil.data.repository.PlanRepository

@Composable
fun DetallePlanScreen(
    planId: String?,
    yaContratado: Boolean,
    onContratar: () -> Unit,
    onVolver: () -> Unit,
    viewModel: DetalleViewModel = viewModel()
) {
    val plan = PlanRepository.planes.find { it.id == planId }
    val contratacionExitosa by viewModel.contratacionExitosa.collectAsState()

    LaunchedEffect(contratacionExitosa) {
        if (contratacionExitosa) {
            onContratar()
        }
    }

    if (plan == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Plan no encontrado")
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(text = plan.nombrePlan, style = MaterialTheme.typography.headlineMedium)
        Text(text = plan.aseguradora, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Tipo: ${plan.tipo.name}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Cobertura:")
        Text(text = plan.cobertura, style = MaterialTheme.typography.bodyMedium)

        plan.deducible?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Deducible: S/ $it")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "S/ ${plan.primaMensual} / mes",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.weight(1f))

        if (yaContratado) {
            OutlinedButton(onClick = onVolver, modifier = Modifier.fillMaxWidth()) {
                Text("Volver")
            }
        } else {
            Button(
                onClick = { viewModel.contratar(plan.id) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Contratar")
            }
        }
    }
}