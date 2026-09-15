package com.example.segurosfacil.navigation

sealed class Screen(val route: String) {
    object Cargando : Screen("cargando")
    object Login : Screen("login")
    object Home : Screen("home")

    object Cotizador : Screen("cotizador")
    object MisPolizas : Screen("mis_polizas")

    object ReportarSiniestro : Screen("reportar_siniestro")
    object DetallePlan : Screen("detalle_plan/{planId}?yaContratado={yaContratado}") {
        fun crearRuta(planId: String, yaContratado: Boolean = false) =
            "detalle_plan/$planId?yaContratado=$yaContratado"
    }
}