package com.example.android.culturalwaves.data.entities

import com.google.gson.annotations.SerializedName


data class ImageDetail(
    @SerializedName("baseimageurl") val baseImageUrl: String?,
    @SerializedName("imageid") val imageId: Int?,
    @SerializedName("format") val format: String?,
    @SerializedName("description") val description: String?
)