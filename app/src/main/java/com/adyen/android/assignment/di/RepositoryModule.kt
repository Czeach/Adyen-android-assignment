package com.adyen.android.assignment.di

import com.adyen.android.assignment.data.repository.APODRepository
import com.adyen.android.assignment.data.repository.APODRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
abstract class RepositoryModule {

    @[Binds Singleton]
    abstract fun provideAPODRepositoryImpl(
        apodRepositoryImpl: APODRepositoryImpl
    ): APODRepository
}