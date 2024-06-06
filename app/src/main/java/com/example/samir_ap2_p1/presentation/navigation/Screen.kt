package com.example.samir_ap2_p1.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object ServicioList: Screen()

    @Serializable
    data class RegistroServicio(val servicioId: Int): Screen()
}