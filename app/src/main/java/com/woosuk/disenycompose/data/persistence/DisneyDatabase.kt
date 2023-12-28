package com.woosuk.disenycompose.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.woosuk.disenycompose.data.persistence.entitiy.PosterEntity

@Database(entities = [PosterEntity::class], version = 2, exportSchema = false)
abstract class DisneyDatabase : RoomDatabase() {
    abstract fun posterDao(): PosterDao
}
