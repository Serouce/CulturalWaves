package com.example.android.culturalwaves.model

import com.google.gson.annotations.SerializedName

data class Artist(
    @SerializedName("name") val name: String?,
    @SerializedName("role") val role: String?
)
