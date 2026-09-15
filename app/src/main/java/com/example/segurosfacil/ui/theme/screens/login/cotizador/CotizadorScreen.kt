package com.example.segurosfacil.ui.screens.cotizador

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.segurosfacil.data.model.TipoSeguro
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import com.example.segurosfacil.ui.components.PlanCard

@Composable
fun CotizadorScreen(
    onPlanClick: (String) -> Unit,
    viewModel: CotizadorViewModel = viewModel()
) {
    var tipoSeleccionado by remember { mutableStateOf<TipoSeguro?>(null) }
    var presupuesto by remember { mutableStateOf(100f) }

    val planSugerido by viewModel.planSugerido.collectAsState()
    val sinResultados by viewModel.sinResultados.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Cotizador", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Tipo de seguro")
        Spacer(modifier = Modifier.height(8.dp))
        Column {
            TipoSeguro.entries.forEach { tipo ->
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    RadioButton(
                        selected = tipoSeleccionado == tipo,
                        onClick = { tipoSeleccionado = tipo }
                    )
                    Text(text = tipo.name)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Presupuesto mensual máximo: S/ ${presupuesto.toInt()}")
        Slider(
            value = presupuesto,
            onValueChange = { presupuesto = it },
            valueRange = 20f..300f
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                tipoSeleccionado?.let { viewModel.cotizar(it, presupuesto.toDouble()) }
            },
            enabled = tipoSeleccionado != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver plan sugerido")
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (sinResultados) {
            Text(text = "No hay planes de ese tipo dentro de tu presupuesto. Intenta subir el presupuesto.")
        }

        planSugerido?.let { plan ->
            Text(text = "Plan sugerido", style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(4.dp))
            PlanCard(plan = plan, onClick = { onPlanClick(plan.id) })
        }
    }
}