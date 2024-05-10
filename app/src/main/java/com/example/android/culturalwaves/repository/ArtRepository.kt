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


private const val API_KEY = "457bf8b1-0c12-46bd-8f80-bd7ff41905d6"


class ArtRepository(private val apiService: ArtMuseumApiService) {
    private val pagerConfigurator = PagerConfigurator()

    fun getArtworksStream(): Flow<PagingData<Artwork>> {
        return Pager(
            config = pagerConfigurator.getDefaultConfig(),
            pagingSourceFactory = { ArtworkPagingSource(apiService, API_KEY) }
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


// val artRepository = ArtRepository(RetrofitClient.instance)
