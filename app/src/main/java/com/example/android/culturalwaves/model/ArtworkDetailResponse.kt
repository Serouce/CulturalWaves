package com.example.android.culturalwaves.model

import com.google.gson.annotations.SerializedName

data class ArtworkDetailResponse(
    @SerializedName("objectid") val objectId: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("primaryimageurl") val imageUrl: String?,
    @SerializedName("people") val people: List<Artist>?,
    @SerializedName("description") val description: String?,
    @SerializedName("images") val images: List<ImageDetail>?,
    @SerializedName("technique") val technique: String?,
    @SerializedName("provenance") val provenance: String?, // Новое поле
    @SerializedName("period") val period: String?, // Новое поле
    @SerializedName("dimensions") val dimensions: String?

)