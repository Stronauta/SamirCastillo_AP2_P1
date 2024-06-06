package com.example.samir_ap2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.samir_ap2_p1.data.local.database.ServicioDb
import com.example.samir_ap2_p1.presentation.navigation.Parcial1NavHost
import com.example.samir_ap2_p1.ui.theme.Samir_AP2_P1Theme

class MainActivity : ComponentActivity() {
    private lateinit var ServicioDb: ServicioDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ServicioDb = Room.databaseBuilder(
            this,
            ServicioDb::class.java,
            "Servicio.db"
        )
            .fallbackToDestructiveMigration()
            .build()

        enableEdgeToEdge()
        setContent {
            Samir_AP2_P1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {

                    }
                    val navHost = rememberNavController()

   /*                 Parcial1NavHost(
                        navHostController = navHost,
                        repository = ServicioDb.ServiciosDao
                    )*/
                }
            }
        }
    }
}

