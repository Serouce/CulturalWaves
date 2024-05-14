package com.example.android.culturalwaves.domain.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android.culturalwaves.data.entities.Artwork
import com.example.android.culturalwaves.data.network.ArtMuseumApiService
import retrofit2.HttpException
import java.io.IOException

class ArtworkPagingSource(
    private val apiService: ArtMuseumApiService,
    private val apiKey: String,
    private val queryParams: Map<String, String> = emptyMap()
) : PagingSource<Int, Artwork>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artwork> {
        val pageNumber = params.key ?: 1
        return try {
            // Обновляем запрос, включая дополнительные параметры поиска
            val response = apiService.fetchArtworks(
                apiKey,
                pageNumber,
                params.loadSize,
                queryParams["classification"],
                "random",
                queryParams["title"],
                queryParams["person"],
                queryParams["description"]
            )
            val body = response.body()
            val artworks = body?.records ?: emptyList()
            val nextPageNumber = if (artworks.isEmpty()) null else pageNumber + 1

            LoadResult.Page(
                data = artworks,
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = nextPageNumber
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Artwork>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1) ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}
