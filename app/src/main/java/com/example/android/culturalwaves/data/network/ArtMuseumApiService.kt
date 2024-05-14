package com.example.android.culturalwaves.data.network

import com.example.android.culturalwaves.data.entities.ArtResponse
import com.example.android.culturalwaves.data.entities.ArtworkDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Интерфейс для взаимодействия с API музея искусств.
 */
interface ArtMuseumApiService {
    /**
     * Получает список произведений искусства.
     *
     * @param apiKey API ключ для доступа к сервису.
     * @param page Номер страницы для пагинации (по умолчанию 1).
     * @param size Количество элементов на странице (по умолчанию 10).
     * @param classification Классификация произведений искусства для фильтрации (опционально).
     * @param sort Параметр сортировки результатов (по умолчанию "random").
     * @param title Название произведения искусства для фильтрации (опционально).
     * @param person Имя художника для фильтрации (опционально).
     * @param description Описание произведения искусства для фильтрации (опционально).
     * @return Ответ с данными о произведениях искусства.
     */
    @GET("object")
    suspend fun fetchArtworks(
        @Query("apikey") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20,
        @Query("classification") classification: String? = null,
        @Query("sort") sort: String = "random",
        @Query("title") title: String? = null,
        @Query("person") person: String? = null,
        @Query("description") description: String? = null,
    ): Response<ArtResponse>

    /**
     * Получает подробную информацию о конкретном произведении искусства.
     *
     * @param objectID Идентификатор произведения искусства.
     * @param apiKey API ключ для доступа к сервису.
     * @return Ответ с детальной информацией о произведении искусства.
     */
    @GET("object/{objectID}")
    suspend fun fetchArtworkDetails(
        @Path("objectID") objectID: Int,
        @Query("apikey") apiKey: String
    ): Response<ArtworkDetailResponse>
}
