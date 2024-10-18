package com.adyen.android.assignment.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class LocalAstronomyPicture(
    @PrimaryKey
    val title: String,
    val date: String,
    val explanation: String,
    val url: String,
    val isFavourite: Boolean = false
)