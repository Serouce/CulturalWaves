package com.example.android.culturalwaves.di

import androidx.room.Room
import com.example.android.culturalwaves.data.database.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "culturalwaves_db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<AppDatabase>().favoriteArtworkDao() }
}