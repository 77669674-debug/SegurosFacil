package com.example.segurosfacil.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.segurosfacil.data.repository.SessionManager
import com.example.segurosfacil.ui.screens.cotizador.CotizadorScreen
import com.example.segurosfacil.ui.screens.detalle.DetallePlanScreen
import com.example.segurosfacil.ui.screens.home.HomeScreen
import com.example.segurosfacil.ui.screens.login.LoginScreen
import com.example.segurosfacil.ui.screens.polizas.MisPolizasScreen
import com.example.segurosfacil.ui.screens.siniestro.ReportarSiniestroScreen
import kotlinx.coroutines.flow.first
import androidx.compose.runtime.getValue

private val rutasConBarra = listOf(
    Screen.Home.route,
    Screen.Cotizador.route,
    Screen.MisPolizas.route,
    Screen.ReportarSiniestro.route
)

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val backStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (rutaActual in rutasConBarra) {
                NavigationBar {
                    NavigationBarItem(
                        selected = rutaActual == Screen.Home.route,
                        onClick = { navController.navigate(Screen.Home.route) },
                        icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                        label = { Text("Home") }
                    )
                    NavigationBarItem(
                        selected = rutaActual == Screen.Cotizador.route,
                        onClick = { navController.navigate(Screen.Cotizador.route) },
                        icon = { Icon(Icons.Filled.Search, contentDescription = "Cotizador") },
                        label = { Text("Cotizar") }
                    )
                    NavigationBarItem(
                        selected = rutaActual == Screen.MisPolizas.route,
                        onClick = { navController.navigate(Screen.MisPolizas.route) },
                        icon = { Icon(Icons.Filled.List, contentDescription = "Mis pólizas") },
                        label = { Text("Mis Pólizas") }
                    )
                    NavigationBarItem(
                        selected = rutaActual == Screen.ReportarSiniestro.route,
                        onClick = { navController.navigate(Screen.ReportarSiniestro.route) },
                        icon = { Icon(Icons.Filled.Warning, contentDescription = "Siniestro") },
                        label = { Text("Siniestro") }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Cargando.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Cargando.route) {
                androidx.compose.runtime.LaunchedEffect(Unit) {
                    val usuarioId = SessionManager.obtenerUsuarioId(context).first()
                    val destino = if (usuarioId != null) Screen.Home.route else Screen.Login.route
                    navController.navigate(destino) {
                        popUpTo(Screen.Cargando.route) { inclusive = true }
                    }
                }
            }
            composable(Screen.Login.route) {
                LoginScreen(
                    onLoginExitoso = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.Home.route) {
                HomeScreen(
                    onPlanClick = { planId ->
                        navController.navigate(Screen.DetallePlan.crearRuta(planId))
                    }
                )
            }
            composable(Screen.Cotizador.route) {
                CotizadorScreen(
                    onPlanClick = { planId ->
                        navController.navigate(Screen.DetallePlan.crearRuta(planId))
                    }
                )
            }
            composable(Screen.MisPolizas.route) {
                MisPolizasScreen(
                    onPlanClick = { planId ->
                        navController.navigate(Screen.DetallePlan.crearRuta(planId, yaContratado = true))
                    }
                )
            }
            composable(Screen.ReportarSiniestro.route) {
                ReportarSiniestroScreen()
            }
            composable(
                Screen.DetallePlan.route,
                arguments = listOf(
                    navArgument("planId") { type = NavType.StringType },
                    navArgument("yaContratado") {
                        type = NavType.BoolType
                        defaultValue = false
                    }
                )
            ) { backStackEntry ->
                DetallePlanScreen(
                    planId = backStackEntry.arguments?.getString("planId"),
                    yaContratado = backStackEntry.arguments?.getBoolean("yaContratado") ?: false,
                    onContratar = { navController.popBackStack() },
                    onVolver = { navController.popBackStack() }
                )
            }
        }
    }
}