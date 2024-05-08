package com.example.android.culturalwaves.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android.culturalwaves.model.Artwork
import com.example.android.culturalwaves.network.ArtMuseumApiService
import retrofit2.HttpException
import java.io.IOException

class ArtworkPagingSource(
    private val apiService: ArtMuseumApiService,
    private val apiKey: String = "457bf8b1-0c12-46bd-8f80-bd7ff41905d6"
) : PagingSource<Int, Artwork>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artwork> {
        val pageNumber = params.key ?: 1  // Если ключ страницы не предоставлен, начинаем с первой страницы
        return try {
            val response = apiService.fetchArtworks(apiKey, pageNumber, params.loadSize)
            val body = response.body()
            val artworks = body?.records ?: emptyList()
            val nextPageNumber = if (artworks.isEmpty()) null else pageNumber + 1
            LoadResult.Page(
                data = artworks,
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = nextPageNumber
            )
        } catch (e: IOException) {
            // IOException для обработки сетевых ошибок, например, при отсутствии интернета
            LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException для обработки неправильных ответов HTTP, например, если сервер вернул ошибку 404 или 500
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Artwork>): Int? {
        // Этот метод помогает определить ключ страницы для перезагрузки данных после изменений в конфигурации
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1) ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}