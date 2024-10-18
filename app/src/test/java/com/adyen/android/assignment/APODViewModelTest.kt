package com.adyen.android.assignment

import androidx.lifecycle.SavedStateHandle
import com.adyen.android.assignment.data.api.model.AstronomyPicture
import com.adyen.android.assignment.data.DataState
import com.adyen.android.assignment.data.local.model.LocalAstronomyPicture
import com.adyen.android.assignment.data.repository.APODRepository
import com.adyen.android.assignment.ui.viewmodel.APODViewModel
import com.adyen.android.assignment.ui.state.APODListState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class APODViewModelTest {

    private lateinit var mockAPODRepository: APODRepository
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: APODViewModel

    @get:Rule
    val coroutinesRule = CoroutinesRule()

    private val mockResponse = listOf(
        AstronomyPicture(
            date = "2006-04-15",
            explanation = "In this stunning cosmic vista, galaxy M81 is on the left ...",
            title = "The milky way and the Andromeda galaxy",
            url = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c80.jpg",
            hdurl = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c80.jpg",
            mediaType = "image",
            serviceVersion = "v1"
        )
    )

    private val mockLocalResponse = listOf(
        LocalAstronomyPicture(
            title = "The milky way and the Andromeda galaxy",
            date = "15/04/2006",
            explanation = "In this stunning cosmic vista, galaxy M81 is on the left ...",
            url = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c80.jpg",
            isFavourite = true,
        )
    )

    @Before
    fun setUp() {
        val state = mapOf(
            "title" to "The Extraordinary Spiral in LL Pegasi",
            "date" to "2023-01-01",
            "explanation" to "hat created the strange spiral structure on the upper left? ...",
            "url" to "https://apod.nasa.gov/apod/image/1510/PlutoMoons_NewHorizons_1080.jpg"
        )
        savedStateHandle = SavedStateHandle(state)
        mockAPODRepository = mockk<APODRepository>(relaxed = true)
        viewModel = APODViewModel(savedStateHandle, mockAPODRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `savedStateHandle returns appropriate values`() {
        assertEquals(viewModel.apodTitle, "The Extraordinary Spiral in LL Pegasi")
        assertEquals(viewModel.apodDate, "2023-01-01")
        assertEquals(viewModel.apodExplanation, "hat created the strange spiral structure on the upper left? ...")
        assertEquals(viewModel.apodUrl, "https://apod.nasa.gov/apod/image/1510/PlutoMoons_NewHorizons_1080.jpg")
    }

    @Test
    fun `apodsState initial state is Loading`() = runTest {
        assertEquals(APODListState.Loading, viewModel.apodsState.value)
    }

    @Test
    fun `apodsState emits Success state when data is fetched successfully from repository`() =
        runTest {
            val dataState = DataState.success(data = mockResponse)
            coEvery { mockAPODRepository.getAPODs() } returns flow { emit(dataState) }

            viewModel.getAPODs()

            assertEquals(APODListState.Success(data = mockResponse), viewModel.apodsState.value)
        }

    @Test
    fun `apodsState emits error state when repository throws an error`() =
        runTest {
            val exception = Exception("Error fetching pictures")
            val dataState = DataState.error<List<AstronomyPicture>>(message = exception.message!!)
            coEvery { mockAPODRepository.getAPODs() } returns  flow { emit(dataState) }

            viewModel.getAPODs()

            assertEquals(APODListState.Error(exception.message), viewModel.apodsState.value)
        }

    @Test
    fun `favouriteState is true when database returns picture object`() = runTest {
        coEvery { mockAPODRepository.getLocalAPOD(mockLocalResponse[0].title) } returns mockLocalResponse[0]

        viewModel.getAPODByTitle(mockLocalResponse[0].title)

        assertEquals(viewModel.favouriteState.value, true)
    }

    @Test
    fun `favouriteState is false when database returns null`() = runTest {
        coEvery { mockAPODRepository.getLocalAPOD(mockLocalResponse[0].title) } returns null

        viewModel.getAPODByTitle(mockLocalResponse[0].title)

        assertEquals(viewModel.favouriteState.value, false)
    }

    @Test
    fun `insertAPOD() calls repository insert method`() = runTest {
        coEvery { mockAPODRepository.insertAPOD(mockLocalResponse[0]) } returns Unit

        viewModel.insertAPOD(mockLocalResponse[0])

        coVerify { mockAPODRepository.insertAPOD(mockLocalResponse[0]) }
    }

    @Test
    fun `deleteAPOD() calls repository delete method`() = runTest {
        coEvery { mockAPODRepository.deleteAPOD(mockLocalResponse[0].title) } returns Unit

        viewModel.deleteAPOD(mockLocalResponse[0].title)

        coVerify { mockAPODRepository.deleteAPOD(mockLocalResponse[0].title) }
    }

    @Test
    fun `onOrderByTitleStateChanged() updates orderByTitleState with new value`() {
        assert(!viewModel.orderByTitleState.value)

        val value = true

        viewModel.onOrderByTitleStateChanged(value)

        assert(viewModel.orderByTitleState.value == value)
    }

    @Test
    fun `onOrderByDateStateChanged() updates orderByDateState with new value`() {
        assert(!viewModel.orderByDateState.value)

        val newValue = true

        viewModel.onOrderByDateStateChanged(newValue)

        assert(viewModel.orderByDateState.value == newValue)
    }

    @Test
    fun `onFavouriteStateChanged() updates favouriteState`() {
        assert(!viewModel.favouriteState.value)

        val newValue = true

        viewModel.onFavouriteStateChanged(true)

        assert(viewModel.favouriteState.value == newValue)
    }
}