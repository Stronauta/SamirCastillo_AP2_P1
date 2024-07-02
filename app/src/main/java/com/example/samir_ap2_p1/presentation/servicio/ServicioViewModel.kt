package com.example.samir_ap2_p1.presentation.servicio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samir_ap2_p1.data.local.entities.ServiciosEntity
import com.example.samir_ap2_p1.data.repository.ServicoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServicioViewModel @Inject constructor(
    private val repository: ServicoRepository,
) : ViewModel() {

    private val servicioId: Int = 0


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

    fun onDescripcionChanged(descripcion: String) {
        val descripcionError: String? = when {
            descripcion.isEmpty() -> "Campo Obligatorio"
            else -> null
        }
        if (!descripcion.startsWith(" ")){
            uiState.update {
                it.copy(
                    descripcion = descripcion,
                    isDescripcionError = descripcionError
                )
            }
        }
    }

    fun onPrecioChanged(precioStr: String) {
        val regex = Regex("[0-9]{0,7}\\.?[0-9]{0,2}")
        if (precioStr.matches(regex)) {
            val total = precioStr.toDoubleOrNull() ?: 0.0
            uiState.update {
                it.copy(
                    precio = total,
                    isPrecioError = null
                )
            }
        }
    }

    fun validate(): Boolean{
        val descripcionEmpty = uiState.value.descripcion.isEmpty()
        val precioEmpty = ((uiState.value.precio ?: 0.0) <= 0.0)
        if(descripcionEmpty){
            uiState.update { it.copy(isDescripcionError = "Campo Obligatorio.") }
        }
        if(precioEmpty){
            uiState.update { it.copy(isPrecioError = "Precio debe ser mayor que 0.0") }
        }
        return !descripcionEmpty && !precioEmpty
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

private fun descripcionExist(descripcion: String, servicioId: Int?): Boolean {
    return articulos.value.any { it.descripcion?.replace(" ", "")?.uppercase() == descripcion.replace(" ", "").uppercase() && it.articuloId != servicioId }
}*/
