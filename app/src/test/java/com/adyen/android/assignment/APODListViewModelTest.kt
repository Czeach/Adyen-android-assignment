package com.adyen.android.assignment

import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.data.DataState
import com.adyen.android.assignment.data.repository.APODRepository
import com.adyen.android.assignment.ui.screens.list.APODListViewModel
import com.adyen.android.assignment.ui.states.APODListState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class APODListViewModelTest {

    private lateinit var mockAPODRepository: APODRepository
    private lateinit var viewModel: APODListViewModel

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

    @Before
    fun setUp() {
        mockAPODRepository = mockk<APODRepository>(relaxed = true)
        viewModel = APODListViewModel(mockAPODRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
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
}