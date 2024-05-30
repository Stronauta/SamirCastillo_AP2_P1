package com.example.samir_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object List: Screen()

    @Serializable
    data class Registro(val id: Int): Screen()
}