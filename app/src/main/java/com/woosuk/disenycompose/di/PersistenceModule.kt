package com.woosuk.disenycompose.di

import android.app.Application
import androidx.room.Room
import com.woosuk.disenycompose.data.persistence.DisneyDatabase
import com.woosuk.disenycompose.data.persistence.PosterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    private const val DATABASE_NAME = "DISNEY_DATABASE"

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application,
    ): DisneyDatabase = Room.databaseBuilder(
        application,
        DisneyDatabase::class.java,
        DATABASE_NAME,
    ).fallbackToDestructiveMigration() // 데이터베이스 마이그레이션 시, 기존 데이터를 다 날리고 마이그레이션 한다.
        .build()

    @Provides
    @Singleton
    fun providePosterDao(
        disneyDatabase: DisneyDatabase,
    ): PosterDao = disneyDatabase.posterDao()
}
