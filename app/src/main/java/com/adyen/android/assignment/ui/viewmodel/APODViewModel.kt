package com.adyen.android.assignment.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.data.DataState
import com.adyen.android.assignment.data.api.model.AstronomyPicture
import com.adyen.android.assignment.data.local.model.LocalAstronomyPicture
import com.adyen.android.assignment.data.repository.APODRepository
import com.adyen.android.assignment.ui.viewmodel.state.APODListState
import com.adyen.android.assignment.utils.Utils.sortAPODSByDate
import com.adyen.android.assignment.utils.Utils.sortAPODsByTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class APODViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val apodRepository: APODRepository
): ViewModel() {

    init {
        getAPODs()
    }

    val apodTitle = savedStateHandle.get<String>("title")
    val apodDate = savedStateHandle.get<String>("date")
    val apodExplanation = savedStateHandle.get<String>("explanation")
    val apodUrl = savedStateHandle.get<String>("url")

    private val _favourites = MutableStateFlow(emptyList<LocalAstronomyPicture>())
    val favourites: StateFlow<List<LocalAstronomyPicture>> = _favourites

    private val _apodsState = MutableStateFlow<APODListState>(APODListState.Loading)
    val apodsState: StateFlow<APODListState> = _apodsState

    private var currentAPODs: List<AstronomyPicture>? = null

    private val _orderByTitleState = MutableStateFlow(false)
    val orderByTitleState: StateFlow<Boolean>
        get() = _orderByTitleState

    private val _orderByDateState = MutableStateFlow(false)
    val orderByDateState: StateFlow<Boolean>
        get() = _orderByDateState

    private val _favouriteState = MutableStateFlow(false)
    val favouriteState: StateFlow<Boolean>
        get() = _favouriteState

    fun getLocalAPODs() {
        viewModelScope.launch {
            val favourites = apodRepository.getLocalAPODList()
            if (favourites.isNotEmpty()) {
                _favourites.update { favourites }
            } else {
                _favourites.update { emptyList() }
            }
        }
    }

    fun getAPODs() {
        viewModelScope.launch {
            apodRepository.getAPODs().collect { dataState ->
                _apodsState.emit(dataState.toAPODListState())
            }
        }
    }

    private fun DataState<List<AstronomyPicture>>.toAPODListState(): APODListState {
        return when {
            isLoading -> APODListState.Loading
            isSuccess -> {
                currentAPODs = data
                APODListState.Success(data)
            }
            else -> APODListState.Error(message)
        }
    }

    fun reOrderList() {
        if (orderByDateState.value) {
            currentAPODs?.let {
                val sortedList = sortAPODSByDate(it)
                _apodsState.update { APODListState.Success(sortedList) }
            }
        }
        else if (orderByTitleState.value) {
            currentAPODs?.let {
                val sortedList = sortAPODsByTitle(it)
                _apodsState.update { APODListState.Success(sortedList) }
            }
        } else {
            _apodsState.update { APODListState.Success(currentAPODs) }
        }
    }

    fun onOrderByTitleStateChanged(value: Boolean) {
        _orderByTitleState.update { value }
    }

    fun onOrderByDateStateChanged(value: Boolean) {
        _orderByDateState.update { value }
    }

    fun getAPODByTitle(title: String) {
        viewModelScope.launch {
            val localAPOD = apodRepository.getLocalAPOD(title)
            if (localAPOD != null) {
                _favouriteState.update { localAPOD.isFavourite }
            }
        }
    }

    fun insertAPOD(apod: LocalAstronomyPicture) {
        viewModelScope.launch {
            apodRepository.insertAPOD(apod)
        }
    }

    fun deleteAPOD(title: String) {
        viewModelScope.launch {
            apodRepository.deleteAPOD(title)
        }
    }

    fun onFavouriteStateChanged(value: Boolean) {
        _favouriteState.update { value }
    }
}