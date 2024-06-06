package com.example.samir_ap2_p1.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.samir_ap2_p1.data.local.dao.ServiciosDao
import com.example.samir_ap2_p1.data.local.entities.ServiciosEntity

@Database(
    entities = [
        ServiciosEntity::class
    ],
    version = 2,
    exportSchema = false
)


abstract class ServicioDb : RoomDatabase(){
    abstract fun servicioDao(): ServiciosDao
}

