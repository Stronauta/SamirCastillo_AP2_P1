package com.example.samir_ap2_p1.data.repository

import com.example.samir_ap2_p1.data.local.dao.ServiciosDao
import com.example.samir_ap2_p1.data.local.entities.ServiciosEntity

class ServicoRepository(
    private val ServicoDao: ServiciosDao
) {
    suspend fun saveServicio(servicio: ServiciosEntity) = ServicoDao.save(servicio)

    suspend fun deleteServicio(servicio: ServiciosEntity) = ServicoDao.delete(servicio)

    suspend fun getServicio(id: Int) = ServicoDao.find(id)

    fun getServicios() = ServicoDao.getAll()
}

