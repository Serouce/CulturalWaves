package com.example.android.culturalwaves.data

import com.example.android.culturalwaves.data.entities.ArtworkDetailResponse
import com.example.android.culturalwaves.data.network.ArtMuseumApiService
import com.example.android.culturalwaves.data.repositories.ArtRepository
import com.example.android.culturalwaves.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class ArtRepositoryTest {

    @Mock
    private lateinit var apiService: ArtMuseumApiService

    private lateinit var artRepository: ArtRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        artRepository = ArtRepository(apiService)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun fetchArtworkDetails_success() = runTest {
        val mockResponse = ArtworkDetailResponse(
            objectId = 1,
            title = "Artwork",
            imageUrl = null,
            people = null,
            description = null,
            images = null,
            technique = null,
            provenance = null,
            period = null,
            dimensions = null
        )

        Mockito.`when`(apiService.fetchArtworkDetails(1, "API_KEY"))
            .thenReturn(Response.success(mockResponse))

        val result = artRepository.fetchArtworkDetails(1)

        assertTrue(result is Result.Success)
        assertEquals(mockResponse, (result as Result.Success).data)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun fetchArtworkDetails_error() = runTest {
        val errorMessage = "Error occurred"
        Mockito.`when`(apiService.fetchArtworkDetails(1, "API_KEY"))
            .thenReturn(Response.error(500, ResponseBody.create("application/json".toMediaTypeOrNull(), errorMessage)))

        val result = artRepository.fetchArtworkDetails(1)

        assertTrue(result is Result.Error)
        assertEquals(errorMessage, (result as Result.Error).exception.message)
    }
}
