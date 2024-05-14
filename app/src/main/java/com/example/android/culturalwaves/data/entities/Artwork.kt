package com.example.android.culturalwaves.data.entities

import com.example.android.culturalwaves.data.entities.Artist
import com.google.gson.annotations.SerializedName

data class Artwork(
    @SerializedName("objectid") val objectId: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("primaryimageurl") val imageUrl: String?,
    @SerializedName("people") val people: List<Artist>?
)