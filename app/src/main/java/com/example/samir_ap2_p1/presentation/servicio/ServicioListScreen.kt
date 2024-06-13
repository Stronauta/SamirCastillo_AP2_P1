package com.example.samir_ap2_p1.presentation.servicio

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.samir_ap2_p1.data.local.entities.ServiciosEntity

@Composable
fun ServiciosListScreen(
    viewModel: ServicioViewModel = hiltViewModel(),
    onAddServicio: () -> Unit,
    onServicioClick: (ServiciosEntity) -> Unit
){
    val servicios by viewModel.servicio.collectAsStateWithLifecycle()
    ServiciosListBody(
        servicios = servicios ?: emptyList(),
        onAddServicio = onAddServicio,
        onServicioClick = onServicioClick
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiciosListBody(
    servicios: List<ServiciosEntity>,
    onAddServicio: () -> Unit,
    onServicioClick: (ServiciosEntity) -> Unit
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Lista de Servicios") },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddServicio) {
                Icon(Icons.Filled.Add, "Agregar servicio")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(4.dp)

        ) {
            ElevatedCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Text(
                        text = "#",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(0.100f)
                    )

                    Spacer(modifier = Modifier.weight(0.05f))


                    Text(
                        text = "Descripción",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(0.40f)
                    )

                    Text(
                        text = "Precio",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(0.40f)
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Gray)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                items(servicios) { servicios ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onServicioClick(servicios) }
                            .padding(horizontal = 4.dp)
                    ) {

                        Spacer(modifier = Modifier.weight(0.05f))

                        Text(
                            text =  servicios.servicioId.toString() +"." ,
                            modifier = Modifier.weight(0.100f)
                        )

                        Spacer(modifier = Modifier.weight(0.05f))

                        Text(
                            text = servicios.descripcion.toString(),
                            modifier = Modifier.weight(0.40f)
                        )

                        Text(
                            text = "RD$ " + servicios.precio.toString(),
                            modifier = Modifier.weight(0.40f)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ListScreenPreview(){
    val servicios = listOf(
        ServiciosEntity(1, "Servicio 1", 10.0),
        ServiciosEntity(2, "Servicio 2", 20.0),
        ServiciosEntity(3, "Servicio 3", 30.0)
    )

    ServiciosListBody(
        servicios = servicios,
        onAddServicio = {},
        onServicioClick = {}
    )
}