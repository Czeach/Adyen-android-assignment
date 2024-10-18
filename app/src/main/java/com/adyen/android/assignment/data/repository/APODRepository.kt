package com.adyen.android.assignment.data.repository

import com.adyen.android.assignment.data.api.model.AstronomyPicture
import com.adyen.android.assignment.data.DataState
import com.adyen.android.assignment.data.local.model.LocalAstronomyPicture
import kotlinx.coroutines.flow.Flow

interface APODRepository {
    fun getAPODs(): Flow<DataState<List<AstronomyPicture>>>

    suspend fun insertAPOD(apod: LocalAstronomyPicture)

    suspend fun getLocalAPODList(): List<LocalAstronomyPicture>

    suspend fun getLocalAPOD(title: String): LocalAstronomyPicture?

    suspend fun deleteAPOD(title: String)
}