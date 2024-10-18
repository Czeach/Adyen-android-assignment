package com.adyen.android.assignment.di

import androidx.room.Room
import com.adyen.android.assignment.data.local.APODDao
import com.adyen.android.assignment.data.local.APODDatabase
import com.adyen.android.assignment.ui.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
object DatabaseModule {
    @[Provides Singleton]
    fun provideDatabase(app: BaseApplication): APODDatabase =
        Room
            .databaseBuilder(
                app,
                APODDatabase::class.java,
                "apod_db"
            )
            .build()

    @[Provides Singleton]
    fun provideAPODDao(db: APODDatabase): APODDao =
        db.apodDao()
}