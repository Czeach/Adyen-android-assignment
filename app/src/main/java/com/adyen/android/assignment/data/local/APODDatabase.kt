package com.adyen.android.assignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.adyen.android.assignment.data.local.model.LocalAstronomyPicture

@Database(entities = [LocalAstronomyPicture::class], version = 1, exportSchema = false)
abstract class APODDatabase: RoomDatabase() {

    abstract fun apodDao(): APODDao
}