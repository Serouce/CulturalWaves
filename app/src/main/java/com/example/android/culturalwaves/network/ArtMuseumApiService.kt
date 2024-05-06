package com.example.android.culturalwaves.network

import com.example.android.culturalwaves.model.ArtResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtMuseumApiService {
    @GET("object")
    suspend fun fetchArtworks(
        @Query("apikey") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10,
        @Query("classification") classification: String? = null,
        @Query("sort") sort: String? = null
    ): Response<ArtResponse>
}