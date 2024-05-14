package com.example.android.culturalwaves.domain.repository

import androidx.paging.PagingConfig

class PagerConfigurator {
    fun getDefaultConfig(): PagingConfig {
        return PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            maxSize = 100
        )
    }
}
