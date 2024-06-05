package com.example.samir_ap2_p1.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.samir_ap2_p1.data.local.entities.ServiciosEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiciosDao {

    @Upsert
    suspend fun save(servicios: ServiciosEntity)
    @Delete
    suspend fun delete(servicios: ServiciosEntity)

    @Query(
        """
            SELECT * 
            FROM servicios
            WHERE servicioId=:id
            LIMIT 1
        """
    )
    suspend fun find(id: Int): ServiciosEntity?

    @Query("SELECT * FROM servicios")
    fun getAll(): Flow<List<ServiciosEntity>>
}