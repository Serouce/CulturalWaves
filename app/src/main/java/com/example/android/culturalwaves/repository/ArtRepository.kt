package com.example.android.culturalwaves.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.android.culturalwaves.model.Artwork
import com.example.android.culturalwaves.model.ArtworkDetailResponse
import com.example.android.culturalwaves.network.ArtMuseumApiService
import com.example.android.culturalwaves.network.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


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
}
