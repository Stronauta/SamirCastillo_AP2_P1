package com.example.samir_ap2_p1.presentation.servicio

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.samir_ap2_p1.R
import com.example.samir_ap2_p1.ui.theme.Samir_AP2_P1Theme


@Composable
fun ServiciosScreen(
    viewModel: ServicioViewModel,
    goBackServiciosListScreen: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val servicios by viewModel.servicio.collectAsStateWithLifecycle()

    ServiciosScreenBody(
        uiState = uiState,
        goBackServiciosListScreen = goBackServiciosListScreen,
        onDescripcionChanged = viewModel::onDescripcionChanged,
        onPrecioChanged = viewModel::onPrecioChanged,
        onSaveServicio = {
            viewModel.saveServicio()
        },
        onDeleteServicio = {
            viewModel.deleteServicio()
        },
        onNewServicio = {
            viewModel.newServicio()
        },
        onValidation = viewModel::validate
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiciosScreenBody(
    uiState: ServicioUiState,
    goBackServiciosListScreen: () -> Unit,
    onDescripcionChanged: (String) -> Unit,
    onPrecioChanged: (String) -> Unit,
    onSaveServicio: () -> Unit,
    onDeleteServicio: () -> Unit,
    onNewServicio: () -> Unit,
    onValidation: () -> Boolean
){
    var guardo by remember { mutableStateOf(false) }
    var errorGuardar by remember { mutableStateOf(false) }
    var elimino by remember { mutableStateOf(false) }
    var errorEliminar by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Registro Servicio") },
                navigationIcon = {
                    IconButton(onClick = goBackServiciosListScreen) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Lista"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {

                    OutlinedTextField(
                        label = { Text(text = "Descripción") },
                        value = uiState.descripcion,
                        onValueChange = onDescripcionChanged,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        isError = uiState.isDescripcionError != null,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Campo Descripción"
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (uiState.isDescripcionError != null) {
                        Text(
                            text = uiState.isDescripcionError ?: "",
                            color = Color.Red,
                            fontStyle = FontStyle.Italic,
                            fontSize = 14.sp
                        )
                    }

                    OutlinedTextField(
                        label = { Text(text = "Precio") },
                        value = uiState.precio.toString().replace("null", ""),
                        onValueChange = onPrecioChanged,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                        placeholder = { Text(text = "0.0") },
                        isError = uiState.isPrecioError != null,
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.icons8_moneda_90),
                                contentDescription = "Campo Precio"
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    if(uiState.isPrecioError != null) {
                        Text(
                            text = uiState.isPrecioError ?: "",
                            color = Color.Red,
                            fontStyle = FontStyle.Italic,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        OutlinedButton(
                            onClick = { onNewServicio() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "new button"
                            )
                            Text( text = "New" )
                        }

                        OutlinedButton(
                            onClick = {
                                if (onValidation()) {
                                    onSaveServicio()
                                    guardo = true
                                    goBackServiciosListScreen()
                                } else {
                                    errorGuardar = true
                                }
                            }
                        ) {
                            if (uiState.servicioId == 0 || uiState.servicioId == null) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "save button"
                                )
                            } else {
                                Text(text = "Actualizar")
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "save button"
                                )
                            }
                            Text(text = "Guardar")
                        }

                        OutlinedButton(
                            onClick = {
                                if (uiState.servicioId != null) {
                                    showDialog = true
                                } else {
                                    errorEliminar = true
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "delete button"
                            )
                            Text(text = "Eliminar")
                        }
                        if (elimino) {
                            Toast.makeText(LocalContext.current, "Eliminado Correctamente", Toast.LENGTH_LONG).show()
                            elimino = false
                        }
                        if (errorEliminar) {
                            Toast.makeText(LocalContext.current, "Error al Eliminar", Toast.LENGTH_LONG).show()
                            errorEliminar = false
                        }
                        if (guardo) {
                            Toast.makeText(LocalContext.current, "Servicio Guardado Correctamente", Toast.LENGTH_LONG).show()
                            guardo = false
                        }
                        if (errorGuardar) {
                            Toast.makeText(LocalContext.current, "Error al Guardar", Toast.LENGTH_LONG).show()
                            errorGuardar = false
                        }

                    }

                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text("Eliminar Servicio") },
                            text = { Text("¿Está seguro de que desea eliminar este Servicio?") },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        onDeleteServicio()
                                        showDialog = false
                                        elimino = true
                                        goBackServiciosListScreen()
                                    }
                                ) {
                                    Text("Sí")
                                }
                            },
                            dismissButton = {
                                Button(onClick = { showDialog = false }) {
                                    Text("Cancelar")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun ArticuloScreenPreview() {
    Samir_AP2_P1Theme {
        ServiciosScreenBody(
            uiState = ServicioUiState(),
            goBackServiciosListScreen = { /*TODO*/ },
            onDescripcionChanged = {},
            onPrecioChanged = {},
            onSaveServicio = { /*TODO*/ },
            onDeleteServicio = { /*TODO*/ },
            onNewServicio = { /*TODO*/ },
            onValidation = { false }
        )
    }
}



