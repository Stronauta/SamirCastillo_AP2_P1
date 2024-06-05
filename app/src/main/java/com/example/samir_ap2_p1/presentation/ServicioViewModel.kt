package com.example.samir_ap2_p1.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.samir_ap2_p1.data.local.entities.ServiciosEntity
import com.example.samir_ap2_p1.data.repository.ServicoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ServicioViewModel(
    private val repository: ServicoRepository,
    private val servicioId: Int
) : ViewModel() {

    var uiState = MutableStateFlow(ServicioUiState())
        private set

    val servicio = repository.getServicios()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    init {
        viewModelScope.launch {
            val servicio = repository.getServicio(servicioId)

            servicio?.let {
                uiState.update {
                    it.copy(
                        servicioId = servicio.servicioId,
                        descripcion = servicio.descripcion ?: "",
                        precio = servicio.precio
                    )
                }
            }
        }
    }

    fun saveServicio() {
        viewModelScope.launch {
            repository.saveServicio(uiState.value.toEntity())
            newServicio()
        }
    }

    fun deleteServicio() {
        viewModelScope.launch {
            repository.deleteServicio(uiState.value.toEntity())
        }
    }

    fun newServicio() {
        viewModelScope.launch {
            uiState.value = ServicioUiState()
        }
    }


}


data class ServicioUiState(
    val servicioId: Int? = null,
    var descripcion: String = "",
    var isDescripcionError: String? = null,
    var precio: Double? = null,
    var isPrecioError: String? = null,
)

fun ServicioUiState.toEntity() = ServiciosEntity(
    servicioId = servicioId,
    descripcion = descripcion,
    precio = precio
)

/*


fun saveArticulo() {
    viewModelScope.launch {
        repository.saveArticulo(uiState.value.toEntity())
        newArticulo()
    }
}

fun deleteArticulo() {
    viewModelScope.launch {
        repository.deleteArticulo(uiState.value.toEntity())
    }
}

fun newArticulo() {
    viewModelScope.launch {
        uiState.value = ArticuloUIState()
    }
}

fun onDescripcionChanged(descripcion: String) {
    val descripcionError: String? = when {
        descripcion.isEmpty() -> "Campo Obligatorio"
        descripcionExist(descripcion, uiState.value.articuloId) -> "Descripcion Existente"
        else -> null
    }
    if (!descripcion.startsWith(" ")){
        uiState.update {
            it.copy(
                descripcion = descripcion,
                descripcionError = descripcionError
            )
        }
    }
}

fun onCostoChanged(costoStr: String){
    val regex = Regex("[0-9]{0,7}\\.?[0-9]{0,2}")
    if (costoStr.matches(regex)) {
        val costo = costoStr.toDoubleOrNull() ?: 0.0
        uiState.update {
            it.copy(
                costo = costo,
                costoError = null
            )
        }
        getPrecio()
    }
}

fun onGananciaChanged(gananciaStr: String){
    val regex = Regex("[0-9]{0,7}\\.?[0-9]{0,2}")
    if (gananciaStr.matches(regex)) {
        val ganancia = gananciaStr.toDoubleOrNull() ?: 0.0
        uiState.update {
            it.copy(
                ganancia = ganancia
            )
        }
    }
    getPrecio()
}
fun onPrecioChanged(precioStr: String){
    val precio = precioStr.toDoubleOrNull() ?: 0.0
    uiState.update {
        it.copy(
            precio = precio,
        )
    }
}
private fun getPrecio() {
    val precio = uiState.value.costo?.plus((uiState.value.costo ?: 0.0) * ((uiState.value.ganancia ?: 0.0)/100))
    uiState.update {
        it.copy(
            precio = precio
        )
    }
}

fun validation(): Boolean {
    val descripcionEmpty = uiState.value.descripcion.isEmpty()
    val descripcionRepeated = descripcionExist(uiState.value.descripcion, uiState.value.articuloId)
    val costoEmpty = ((uiState.value.costo ?: 0.0) <= 0.0)
    val precioEmpty = ((uiState.value.precio ?: 0.0) <= 0.0)
    if (descripcionEmpty) {
        uiState.update { it.copy(descripcionError = "Campo Obligatorio.") }
    }
    if (descripcionRepeated) {
        uiState.update { it.copy(descripcionError = "El nombre está repetido.") }
    }
    if (costoEmpty) {
        uiState.update { it.copy(costoError = "Costo debe ser mayor que 0.0") }
    }
    if (precioEmpty) {
        uiState.update { it.copy(precioError = "Precio debe ser mayor que 0.0") }
    }
    return !descripcionEmpty && !descripcionRepeated && !costoEmpty && !precioEmpty
}

private fun descripcionExist(descripcion: String, id: Int?): Boolean {
    return articulos.value.any { it.descripcion?.replace(" ", "")?.uppercase() == descripcion.replace(" ", "").uppercase() && it.articuloId != id }
}*/
