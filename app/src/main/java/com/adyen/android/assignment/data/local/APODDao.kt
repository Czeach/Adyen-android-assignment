package com.adyen.android.assignment.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adyen.android.assignment.data.local.model.LocalAstronomyPicture

@Dao
interface APODDao {

    @Query("SELECT * FROM favourites")
    suspend fun getAPODs(): List<LocalAstronomyPicture>

    @Query("SELECT * FROM favourites WHERE title = :title")
    suspend fun getAPODByTitle(title: String): LocalAstronomyPicture?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAPOD(apod: LocalAstronomyPicture)

    @Query("DELETE FROM favourites WHERE title = :title")
    suspend fun deleteAPOD(title: String)
}