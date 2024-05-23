package com.example.android.culturalwaves.data.repositories

import androidx.paging.PagingData
import com.example.android.culturalwaves.data.entities.ArtResponse
import com.example.android.culturalwaves.data.entities.Artwork
import com.example.android.culturalwaves.data.entities.ArtworkDetailResponse
import kotlinx.coroutines.flow.Flow
import com.example.android.culturalwaves.utils.Result

interface ArtRepository {
    fun getArtworksStream(queryParams: Map<String, String> = emptyMap()): Flow<PagingData<Artwork>>
    suspend fun fetchArtworkDetails(objectId: Int): Result<ArtworkDetailResponse>
    suspend fun fetchArtworksForSuggestions(query: String): Result<ArtResponse>
}