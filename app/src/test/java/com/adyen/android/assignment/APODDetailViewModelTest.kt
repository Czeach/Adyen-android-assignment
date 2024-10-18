package com.adyen.android.assignment

import androidx.lifecycle.SavedStateHandle
import com.adyen.android.assignment.ui.screens.details.APODDetailViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class APODDetailViewModelTest {

    private lateinit var viewModel: APODDetailViewModel
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setUp() {
        val state = mapOf(
            "title" to "The Extraordinary Spiral in LL Pegasi",
            "date" to "2023-01-01",
            "explanation" to "hat created the strange spiral structure on the upper left? ...",
            "url" to "https://apod.nasa.gov/apod/image/1510/PlutoMoons_NewHorizons_1080.jpg"
        )
        savedStateHandle = SavedStateHandle(state)
        viewModel = APODDetailViewModel(savedStateHandle)
    }

    @Test
    fun `savedStateHandle returns appropriate values`() {
        assertEquals(viewModel.apodTitle, "The Extraordinary Spiral in LL Pegasi")
        assertEquals(viewModel.apodDate, "2023-01-01")
        assertEquals(viewModel.apodExplanation, "hat created the strange spiral structure on the upper left? ...")
        assertEquals(viewModel.apodUrl, "https://apod.nasa.gov/apod/image/1510/PlutoMoons_NewHorizons_1080.jpg")
    }

}