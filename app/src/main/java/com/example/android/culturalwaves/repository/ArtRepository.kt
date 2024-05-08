package com.example.android.culturalwaves.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.android.culturalwaves.model.Artwork
import com.example.android.culturalwaves.network.ArtMuseumApiService
import com.example.android.culturalwaves.network.RetrofitClient
import kotlinx.coroutines.flow.Flow


private const val API_KEY = "457bf8b1-0c12-46bd-8f80-bd7ff41905d6"


class ArtRepository(private val apiService: ArtMuseumApiService) {

    fun getArtworksStream(): Flow<PagingData<Artwork>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,               // Определяем размер страницы, который подходит для вашего случая
                enablePlaceholders = false,  // Включаем или отключаем placeholders в зависимости от вашего предпочтения
                maxSize = 100               // Максимальный размер загруженных данных в памяти
            ),
            pagingSourceFactory = { ArtworkPagingSource(apiService, API_KEY) }
        ).flow
    }

    fun getArtPagingSource(): PagingSource<Int, Artwork> {
        return ArtworkPagingSource(apiService)
    }
}

val artRepository = ArtRepository(RetrofitClient.instance)
