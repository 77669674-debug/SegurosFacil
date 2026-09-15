package com.example.segurosfacil.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.segurosfacil.data.model.TipoSeguro
import com.example.segurosfacil.data.repository.PlanRepository
import com.example.segurosfacil.ui.components.PlanCard
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState

@Composable
fun HomeScreen(
    onPlanClick: (String) -> Unit
) {
    var filtroTipo by remember { mutableStateOf<TipoSeguro?>(null) }
    var primaMaxima by remember { mutableStateOf(300f) }

    val planesFiltrados = PlanRepository.planes.filter { plan ->
        (filtroTipo == null || plan.tipo == filtroTipo) &&
                plan.primaMensual <= primaMaxima
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Planes disponibles", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            FilterChip(
                selected = filtroTipo == null,
                onClick = { filtroTipo = null },
                label = { Text("Todos") }
            )
            TipoSeguro.entries.forEach { tipo ->
                FilterChip(
                    selected = filtroTipo == tipo,
                    onClick = { filtroTipo = tipo },
                    label = { Text(tipo.name) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "Prima máxima: S/ ${primaMaxima.toInt()}")
        Slider(
            value = primaMaxima,
            onValueChange = { primaMaxima = it },
            valueRange = 20f..300f
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(planesFiltrados) { plan ->
                PlanCard(plan = plan, onClick = { onPlanClick(plan.id) })
            }
        }
    }
}