package com.example.android.culturalwaves.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.android.culturalwaves.data.entities.ArtResponse
import com.example.android.culturalwaves.data.entities.Artwork
import com.example.android.culturalwaves.data.entities.ArtworkDetailResponse
import com.example.android.culturalwaves.data.network.ArtMuseumApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

private const val API_KEY = "457bf8b1-0c12-46bd-8f80-bd7ff41905d6"


class ArtRepository(private val apiService: ArtMuseumApiService) {
    private val pagerConfigurator = PagerConfigurator()

    fun getArtworksStream(queryParams: Map<String, String>? = null): Flow<PagingData<Artwork>> {
        return Pager(
            config = pagerConfigurator.getDefaultConfig(),
            pagingSourceFactory = {
                queryParams?.let {
                    // Создаем ArtworkPagingSource только если queryParams не null
                    ArtworkPagingSource(apiService, API_KEY, it)
                } ?: ArtworkPagingSource(apiService, API_KEY)
                // В случае null queryParams создаем ArtworkPagingSource без параметров
            }
        ).flow
    }

    suspend fun fetchArtworkDetails(objectId: Int): ArtworkDetailResponse? {
        return try {
            val response = apiService.fetchArtworkDetails(objectId, API_KEY)
            if (response.isSuccessful) {
                response.body()
            } else {
                null // Обработка ошибок может быть расширена
            }
        } catch (e: Exception) {
            null // Логирование или дополнительная обработка исключений
        }
    }

    suspend fun fetchArtworksForSuggestions(query: String): Response<ArtResponse> {
        return apiService.fetchArtworks(
            apiKey = API_KEY,
            title = query,
            size = 5
        ) // Limit results to 5 for suggestions
    }
}


