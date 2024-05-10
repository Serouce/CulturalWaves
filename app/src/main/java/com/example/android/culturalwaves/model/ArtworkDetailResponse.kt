package com.example.android.culturalwaves.model

import com.google.gson.annotations.SerializedName

data class ArtworkDetailResponse(
    @SerializedName("objectid") val objectId: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("primaryimageurl") val imageUrl: String?,
    @SerializedName("people") val people: List<Artist>?,
    @SerializedName("description") val description: String?,
    @SerializedName("images") val images: List<ImageDetail>?
)