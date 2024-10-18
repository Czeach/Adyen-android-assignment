package com.adyen.android.assignment

import com.adyen.android.assignment.data.api.PlanetaryService
import com.adyen.android.assignment.data.api.model.AstronomyPicture
import com.adyen.android.assignment.data.DataState
import com.adyen.android.assignment.data.local.APODDao
import com.adyen.android.assignment.data.local.model.LocalAstronomyPicture
import com.adyen.android.assignment.data.repository.APODRepository
import com.adyen.android.assignment.data.repository.APODRepositoryImpl
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class APODRepositoryImplTest {

    private lateinit var mockPlanetaryService: PlanetaryService
    private lateinit var mockAPODDao: APODDao
    private lateinit var apodRepository: APODRepository

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
        mockPlanetaryService = mockk<PlanetaryService>()
        mockAPODDao = mockk<APODDao>()
        apodRepository = APODRepositoryImpl(mockPlanetaryService, coroutinesRule.testDispatcher, mockAPODDao)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getAPODs() emits loading state first when called`() = runTest {
        coEvery { mockPlanetaryService.getPictures() } returns mockResponse

        val result = apodRepository.getAPODs()

        assertEquals(
            DataState.loading<Unit>(),
            result.first()
        )
    }

    @Test
    fun `getAPODs() emits success state with Astronomy Pictures when network call is successful`() =
        runTest {
            coEvery { mockPlanetaryService.getPictures() } returns mockResponse

            val result = apodRepository.getAPODs()

            assertEquals(
                DataState.success(data = mockResponse.map { it.copy(date = "15/04/2006") }),
                result.last()
            )
        }

    @Test
    fun `getAPODs() emits error state with message when network call fails with exception message`() =
        runTest {
            val exception = Exception("Error fetching pictures")
            coEvery { mockPlanetaryService.getPictures() } throws exception

            val result = apodRepository.getAPODs()

            assertEquals(
                DataState.error<Unit>(message = exception.message!!),
                result.last()
            )
        }

    @Test
    fun `getAPODs() emits error state with default message when there is no exception message`() =
        runTest {
            val exception = Exception()
            coEvery { mockPlanetaryService.getPictures() } throws exception

            val result = apodRepository.getAPODs()

            assertEquals(
                DataState.error<Unit>(message = "Oops! Something went wrong."),
                result.last()
            )
        }

    @Test
    fun `insertAPOD() calls dao insert method`() = runTest {
        coEvery { mockAPODDao.insertAPOD(mockLocalResponse[0]) } returns Unit

        apodRepository.insertAPOD(mockLocalResponse[0])

        coVerify { mockAPODDao.insertAPOD(mockLocalResponse[0]) }
    }

    @Test
    fun `deleteAPOD() calls dao delete method`() = runTest {
        coEvery { mockAPODDao.deleteAPOD(mockLocalResponse[0].title) } returns Unit

        apodRepository.deleteAPOD(mockLocalResponse[0].title)

        coVerify { mockAPODDao.deleteAPOD(mockLocalResponse[0].title) }
    }

    @Test
    fun `getLocalAPOD() returns the correct APOD`() = runTest {
        coEvery { mockAPODDao.getAPODByTitle(mockLocalResponse[0].title) } returns mockLocalResponse[0]

        val result = apodRepository.getLocalAPOD(mockLocalResponse[0].title)

        assertEquals(mockLocalResponse[0], result)
    }

    @Test
    fun `getLocalAPODList() returns the expected response`() = runTest {
        coEvery { mockAPODDao.getAPODs() } returns mockLocalResponse

        val result = apodRepository.getLocalAPODList()

        assertEquals(mockLocalResponse, result)
    }
}