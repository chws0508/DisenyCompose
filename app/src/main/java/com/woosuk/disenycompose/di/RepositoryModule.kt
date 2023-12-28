package com.woosuk.disenycompose.di

import com.woosuk.disenycompose.data.network.DefaultDisneyPosterRepository
import com.woosuk.disenycompose.domain.repository.DisneyPosterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Singleton
    @Binds
    fun provideDisneyPosterRepo(
        defaultDisneyPosterRepository: DefaultDisneyPosterRepository,
    ): DisneyPosterRepository
}
