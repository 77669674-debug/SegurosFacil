package com.example.segurosfacil.ui.screens.polizas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.segurosfacil.ui.components.PlanCard

@Composable
fun MisPolizasScreen(
    onPlanClick: (String) -> Unit,
    viewModel: MisPolizasViewModel = viewModel()
){
    val planesContratados by viewModel.planesContratados.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarPolizas()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Mis pólizas contratadas", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(12.dp))

        if (planesContratados.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Todavía no has contratado ninguna póliza")
            }
        } else {
            LazyColumn {
                items(planesContratados) { plan ->
                    PlanCard(plan = plan, onClick = { onPlanClick(plan.id) })                }
            }
        }
    }
}