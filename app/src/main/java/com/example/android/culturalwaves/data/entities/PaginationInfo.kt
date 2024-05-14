package com.example.android.culturalwaves.data.entities

import com.google.gson.annotations.SerializedName

data class PaginationInfo(
    @SerializedName("totalrecordsperquery") val recordsPerQuery: Int?,
    @SerializedName("totalrecords") val totalRecords: Int?,
    @SerializedName("pages") val pages: Int?,
    @SerializedName("page") val page: Int?,
    @SerializedName("next") val next: String?,
    @SerializedName("prev") val prev: String?,
    @SerializedName("responsetime") val responseTime: String?
)
