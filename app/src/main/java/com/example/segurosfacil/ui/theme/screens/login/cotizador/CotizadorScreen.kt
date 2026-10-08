package com.example.segurosfacil.ui.screens.cotizador

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.segurosfacil.data.model.TipoSeguro
import com.example.segurosfacil.ui.components.PlanCard

@Composable
fun CotizadorScreen(
    onPlanClick: (String) -> Unit,
    viewModel: CotizadorViewModel = viewModel()
) {
    var tipoSeleccionado by remember { mutableStateOf<TipoSeguro?>(null) }
    var coberturaSeleccionada by remember { mutableStateOf<String?>(null) }
    var edadTexto by remember { mutableStateOf("") }

    val planCotizado by viewModel.planCotizado.collectAsState()
    val primaEstimada by viewModel.primaEstimada.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()
    val registroExitoso by viewModel.registroExitoso.collectAsState()

    val coberturasDisponibles = tipoSeleccionado?.let { viewModel.obtenerCoberturasPorTipo(it) } ?: emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Cotizador", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Tipo de seguro *")
        Column {
            TipoSeguro.entries.forEach { tipo ->
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    RadioButton(
                        selected = tipoSeleccionado == tipo,
                        onClick = {
                            tipoSeleccionado = tipo
                            coberturaSeleccionada = null
                        }
                    )
                    Text(text = tipo.name)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (tipoSeleccionado != null) {
            Text(text = "Cobertura *")
            Column {
                coberturasDisponibles.forEach { cobertura ->
                    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                        RadioButton(
                            selected = coberturaSeleccionada == cobertura,
                            onClick = { coberturaSeleccionada = cobertura }
                        )
                        Text(text = cobertura, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        OutlinedTextField(
            value = edadTexto,
            onValueChange = { edadTexto = it.filter { c -> c.isDigit() } },
            label = { Text("Edad *") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (mensaje != null) {
            Text(text = mensaje ?: "", color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                viewModel.cotizar(tipoSeleccionado, coberturaSeleccionada, edadTexto.toIntOrNull())
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular cotización")
        }

        Spacer(modifier = Modifier.height(24.dp))

        planCotizado?.let { plan ->
            Text(text = "Plan cotizado", style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(4.dp))
            PlanCard(plan = plan, onClick = { onPlanClick(plan.id) })

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Prima estimada: S/ ${"%.2f".format(primaEstimada)}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (registroExitoso) {
                Text(text = "✅ Cotización registrada correctamente")
            } else {
                Button(
                    onClick = { viewModel.registrarCotizacion() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrar cotización")
                }
            }
        }
    }
}