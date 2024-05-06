package com.example.android.culturalwaves.model

import com.google.gson.annotations.SerializedName

data class Artwork(
    @SerializedName("objectid") val objectId: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("primaryimageurl") val imageUrl: String?,
    @SerializedName("people") val people: List<Artist>?
)