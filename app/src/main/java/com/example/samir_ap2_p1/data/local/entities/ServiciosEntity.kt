package com.example.samir_ap2_p1.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "servicios")
data class ServiciosEntity(
    @PrimaryKey
    val servicioId: Int? = null,
    var descripcion: String? = "",
    var precio: Double? = 0.0
)
