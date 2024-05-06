package com.example.android.culturalwaves.model

import com.google.gson.annotations.SerializedName

data class ArtResponse(
    @SerializedName("info") val info: PaginationInfo,
    @SerializedName("records") val records: List<Artwork>,
    @SerializedName("aggregations") val aggregations: Map<String, Any>
)
