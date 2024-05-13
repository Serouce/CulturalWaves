package com.example.android.culturalwaves.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_artworks")
data class FavoriteArtwork(
    @PrimaryKey val objectId: Int,
    val title: String,
    val imageUrl: String,
    val description: String,

)