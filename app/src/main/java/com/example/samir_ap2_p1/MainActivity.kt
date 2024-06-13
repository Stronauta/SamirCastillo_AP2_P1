package com.example.samir_ap2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.samir_ap2_p1.data.local.database.ServicioDb
import com.example.samir_ap2_p1.data.repository.ServicoRepository
import com.example.samir_ap2_p1.presentation.navigation.Parcial1NavHost
import com.example.samir_ap2_p1.ui.theme.Samir_AP2_P1Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Samir_AP2_P1Theme {
                val navHostController = rememberNavController()
                Parcial1NavHost(navHostController)
            }
        }
    }
}
