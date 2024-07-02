package com.example.samir_ap2_p1.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.samir_ap2_p1.data.local.dao.ServiciosDao
import com.example.samir_ap2_p1.data.local.database.ServicioDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideServiceadao  (database: ServicioDb): ServiciosDao{
        return database.servicioDao()
    }

    @Provides
    @Singleton
    fun provideServicioDb(@ApplicationContext appContext: Context): ServicioDb{
        return Room.databaseBuilder(
            appContext,
            ServicioDb::class.java,
            "Servicio.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}