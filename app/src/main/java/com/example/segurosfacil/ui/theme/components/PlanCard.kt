package com.example.segurosfacil.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.segurosfacil.data.model.PlanSeguro

@Composable
fun PlanCard(
    plan: PlanSeguro,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = plan.nombrePlan, style = MaterialTheme.typography.titleMedium)
            Text(text = plan.aseguradora, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = plan.cobertura, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "S/ ${plan.primaMensual} / mes",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}