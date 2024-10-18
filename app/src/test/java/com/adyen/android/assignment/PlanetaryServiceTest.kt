package com.adyen.android.assignment

import com.adyen.android.assignment.data.api.PlanetaryService
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlanetaryServiceTest {

    private lateinit var retrofit: Retrofit
    private lateinit var planetaryService: PlanetaryService

    @Before
    fun setup() {
        retrofit = Retrofit
            .Builder()
            .baseUrl(BuildConfig.NASA_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        planetaryService = retrofit.create(PlanetaryService::class.java)
    }

    /**
     * Integration test -
     * ensures the [generated key](https://api.nasa.gov/) returns results from the api
     */
    @Test
    fun `test api_key returns result from api`() = runTest {
        val response = planetaryService.getPictures()
        assert(response.isNotEmpty())
        assert(response.size >1)
    }
}
