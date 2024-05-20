package com.example.android.culturalwaves.data.repositories

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.android.culturalwaves.data.entities.ArtResponse
import com.example.android.culturalwaves.data.entities.Artwork
import com.example.android.culturalwaves.data.entities.ArtworkDetailResponse
import com.example.android.culturalwaves.data.network.ArtMuseumApiService
import com.example.android.culturalwaves.data.paging.ArtworkPagingSource
import com.example.android.culturalwaves.utils.PagerConfigurator
import kotlinx.coroutines.flow.Flow
import com.example.android.culturalwaves.utils.Result


private const val API_KEY = "457bf8b1-0c12-46bd-8f80-bd7ff41905d6"


//class ArtRepository(private val apiService: ArtMuseumApiService) {
//    private val pagerConfigurator = PagerConfigurator()
//
//    fun getArtworksStream(queryParams: Map<String, String> = emptyMap()): Flow<PagingData<Artwork>> {
//        return Pager(
//            config = pagerConfigurator.getDefaultConfig(),
//            pagingSourceFactory = {
//                ArtworkPagingSource(
//                    apiService,
//                    API_KEY,
//                    queryParams
//                )
//            }
//        ).flow
//    }
//
//    suspend fun fetchArtworkDetails(objectId: Int): Result<ArtworkDetailResponse> {
//        return try {
//            val response = apiService.fetchArtworkDetails(objectId, API_KEY)
//            if (response.isSuccessful) {
//                Result.Success(response.body()!!)
//            } else {
//                val exception = Exception("Failed to fetch artwork details: " +
//                        "${response.code()} ${response.message()}")
//                Log.e("ArtRepository", exception.message, exception)
//                Result.Error(exception)
//            }
//        } catch (e: Exception) {
//            Log.e("ArtRepository", "Error fetching artwork details", e)
//            Result.Error(e)
//        }
//    }
//
//    suspend fun fetchArtworksForSuggestions(query: String): Result<ArtResponse> {
//        return try {
//            val response = apiService.fetchArtworks(
//                apiKey = API_KEY,
//                title = query,
//                size = 5
//            )
//            if (response.isSuccessful) {
//                Result.Success(response.body()!!)
//            } else {
//                val exception = Exception("Failed to fetch artworks for suggestions:" +
//                        " ${response.code()} ${response.message()}")
//                Log.e("ArtRepository", exception.message, exception)
//                Result.Error(exception)
//            }
//        } catch (e: Exception) {
//            Log.e("ArtRepository", "Error fetching artworks for suggestions", e)
//            Result.Error(e)
//        }
//    }
//}

class ArtRepository(private val apiService: ArtMuseumApiService) {
    private val pagerConfigurator = PagerConfigurator()

    // Функция возвращает поток PagingData, который используется для ленивой загрузки данных
    fun getArtworksStream(queryParams: Map<String, String> = emptyMap()): Flow<PagingData<Artwork>> {
        return Pager(
            config = pagerConfigurator.getDefaultConfig(), // Конфигурация для пагинации
            pagingSourceFactory = {
                ArtworkPagingSource(
                    apiService,
                    API_KEY,
                    queryParams
                )
            } // Источник данных для пагинации
        ).flow
    }

    // Функция для получения деталей конкретного объекта по его ID
    suspend fun fetchArtworkDetails(objectId: Int): Result<ArtworkDetailResponse> {
        return try {
            val response = apiService.fetchArtworkDetails(objectId, API_KEY)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                val exception = Exception("Failed to fetch artwork details: ${response.code()} ${response.message()}")
                Log.e("ArtRepository", exception.message, exception)
                Result.Error(exception)
            }
        } catch (e: Exception) {
            Log.e("ArtRepository", "Error fetching artwork details", e)
            Result.Error(e)
        }
    }

    // Функция для получения предложений по поисковому запросу
    suspend fun fetchArtworksForSuggestions(query: String): Result<ArtResponse> {
        return try {
            val response = apiService.fetchArtworks(
                apiKey = API_KEY,
                title = query,
                size = 5
            )
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                val exception = Exception("Failed to fetch artworks for suggestions: ${response.code()} ${response.message()}")
                Log.e("ArtRepository", exception.message, exception)
                Result.Error(exception)
            }
        } catch (e: Exception) {
            Log.e("ArtRepository", "Error fetching artworks for suggestions", e)
            Result.Error(e)
        }
    }
}