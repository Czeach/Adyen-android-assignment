package com.adyen.android.assignment.data.repository

import com.adyen.android.assignment.api.PlanetaryService
import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.data.DataState
import com.adyen.android.assignment.utils.Utils.formatDateString
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class APODRepositoryImpl @Inject constructor(
    private val planetaryService: PlanetaryService,
    private val dispatcher: CoroutineDispatcher
): APODRepository {
    override fun getAPODs(): Flow<DataState<List<AstronomyPicture>>> {
        return flow {
            emit(DataState.loading())

            try {
                val apods = planetaryService.getPictures()
                val pictures = apods.filter { it.mediaType == "image" }.map {
                    it.copy(date = formatDateString(it.date))
                }

                emit(DataState.success(data = pictures))
            } catch (e: Exception) {
                emit(DataState.error(message = e.message ?: "Oops! Something went wrong."))
            }
        }.flowOn(dispatcher)
    }
}