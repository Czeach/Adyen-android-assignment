package com.adyen.android.assignment.ui.state

import com.adyen.android.assignment.data.api.model.AstronomyPicture

sealed interface APODListState {
    data class Success(val data: List<AstronomyPicture>?): APODListState
    data class Error(val message: String?): APODListState
    data object Loading: APODListState
}