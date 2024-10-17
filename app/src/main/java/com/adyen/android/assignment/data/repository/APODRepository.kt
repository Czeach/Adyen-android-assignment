package com.adyen.android.assignment.data.repository

import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.data.DataState
import kotlinx.coroutines.flow.Flow

interface APODRepository {
    fun getAPODs(): Flow<DataState<List<AstronomyPicture>>>
}