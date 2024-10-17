package com.adyen.android.assignment.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.data.DataState
import com.adyen.android.assignment.data.repository.APODRepository
import com.adyen.android.assignment.ui.states.APODListState
import com.adyen.android.assignment.utils.Utils.sortAPODSByDate
import com.adyen.android.assignment.utils.Utils.sortAPODsByTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class APODListViewModel @Inject constructor(
    private val apodRepository: APODRepository
): ViewModel() {

    init {
        getAPODs()
    }

    private val _apodsState = MutableStateFlow<APODListState>(APODListState.Loading)
    val apodsState: StateFlow<APODListState> = _apodsState

    private val _orderByTitleState = MutableStateFlow(false)
    val orderByTitleState: StateFlow<Boolean>
        get() = _orderByTitleState

   private val _orderByDateState = MutableStateFlow(false)
    val orderByDateState: StateFlow<Boolean>
        get() = _orderByDateState

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
                if (orderByDateState.value) {
                    return APODListState.Success(data?.let { sortAPODSByDate(it) })
                }
                if (orderByTitleState.value) {
                    return APODListState.Success(data?.let { sortAPODsByTitle(it) })
                }
                APODListState.Success(data)
            }
            else -> APODListState.Error(message)
        }
    }

    fun onOrderByTitleStateChanged(value: Boolean)  {
        _orderByTitleState.value = value
    }

    fun onOrderByDateStateChanged(value: Boolean)  {
        _orderByDateState.value = value
    }
}