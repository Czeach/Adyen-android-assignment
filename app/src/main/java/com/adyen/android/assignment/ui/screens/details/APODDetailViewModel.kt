package com.adyen.android.assignment.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class APODDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    val apodTitle = savedStateHandle.get<String>("title")
    val apodDate = savedStateHandle.get<String>("date")
    val apodExplanation = savedStateHandle.get<String>("explanation")
    val apodUrl = savedStateHandle.get<String>("url")
}