package com.example.android.culturalwaves.data.repositories

import android.util.Log
import androidx.core.os.BuildCompat
import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.android.culturalwaves.BuildConfig
import com.example.android.culturalwaves.data.entities.ArtResponse
import com.example.android.culturalwaves.data.entities.Artwork
import com.example.android.culturalwaves.data.entities.ArtworkDetailResponse
import com.example.android.culturalwaves.data.network.ArtMuseumApiService
import com.example.android.culturalwaves.data.paging.ArtworkPagingSource
import com.example.android.culturalwaves.utils.PagerConfigurator
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import com.example.android.culturalwaves.utils.Result


private const val API_KEY = BuildConfig.API_KEY

class ArtRepositoryImpl(private val apiService: ArtMuseumApiService) : ArtRepository {
    private val pagerConfigurator = PagerConfigurator()

    @OptIn(FlowPreview::class)
    override fun getArtworksStream(queryParams: Map<String, String>): Flow<PagingData<Artwork>> {
        return Pager(
            config = pagerConfigurator.getDefaultConfig(),
            pagingSourceFactory = {
                ArtworkPagingSource(
                    apiService,
                    API_KEY,
                    queryParams
                )
            }
        ).flow
            .debounce(1000)
    }

    override suspend fun fetchArtworkDetails(objectId: Int): Result<ArtworkDetailResponse> {
        return try {
            val response = apiService.fetchArtworkDetails(objectId, API_KEY)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                val exception = Exception("Failed to fetch artwork details: ${response.code()} " +
                        response.message()
                )
                Log.e("ArtRepository", exception.message, exception)
                Result.Error(exception)
            }
        } catch (e: Exception) {
            Log.e("ArtRepository", "Error fetching artwork details", e)
            Result.Error(e)
        }
    }

    override suspend fun fetchArtworksForSuggestions(query: String): Result<ArtResponse> {
        return try {
            val response = apiService.fetchArtworks(
                apiKey = API_KEY,
                title = query,
                size = 5
            )
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                val exception = Exception("Failed to fetch artworks for suggestions:" +
                        " ${response.code()} ${response.message()}")
                Log.e("ArtRepository", exception.message, exception)
                Result.Error(exception)
            }
        } catch (e: Exception) {
            Log.e("ArtRepository", "Error fetching artworks for suggestions", e)
            Result.Error(e)
        }
    }
}