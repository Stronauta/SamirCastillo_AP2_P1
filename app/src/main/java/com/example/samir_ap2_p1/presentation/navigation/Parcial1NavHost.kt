package com.example.samir_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.samir_ap2_p1.data.repository.ServicoRepository
import com.example.samir_ap2_p1.presentation.servicio.ServicioViewModel
import com.example.samir_ap2_p1.presentation.servicio.ServiciosListScreen
import com.example.samir_ap2_p1.presentation.servicio.ServiciosScreen


@Composable
fun Parcial1NavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.ServicioList
    ) {
        composable<Screen.ServicioList>{
            ServiciosListScreen(
                onServicioClick = {
                    navHostController.navigate(Screen.Servicio(it.servicioId ?: 0))
                },
                onAddServicio = {
                    navHostController.navigate(Screen.Servicio(0))
                }
            )
        }

        composable<Screen.Servicio>{
            ServiciosScreen(
                goBackServiciosListScreen = { navHostController.navigate(Screen.ServicioList) }
            )
        }
    }
}

