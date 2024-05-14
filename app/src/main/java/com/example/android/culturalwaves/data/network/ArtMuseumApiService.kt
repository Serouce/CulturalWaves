package com.example.android.culturalwaves.data.network

import com.example.android.culturalwaves.data.entities.ArtResponse
import com.example.android.culturalwaves.data.entities.ArtworkDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtMuseumApiService {
    @GET("object")
    suspend fun fetchArtworks(
        @Query("apikey") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10,
        @Query("classification") classification: String? = null,
        @Query("sort") sort: String = "random",
        @Query("title") title: String? = null,
        @Query("person") person: String? = null,
        @Query("description") description: String? = null
    ): Response<ArtResponse>



    @GET("object/{objectID}")
    suspend fun fetchArtworkDetails(
        @Path("objectID") objectID: Int,
        @Query("apikey") apiKey: String
    ): Response<ArtworkDetailResponse>
}