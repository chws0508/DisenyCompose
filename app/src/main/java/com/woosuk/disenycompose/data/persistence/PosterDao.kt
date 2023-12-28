package com.woosuk.disenycompose.data.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.woosuk.disenycompose.data.persistence.entitiy.PosterEntity

@Dao
interface PosterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePosters(posterEntities: List<PosterEntity>)

    @Query("SELECT * FROM PosterEntity WHERE id = :id_")
    fun getPoster(id_: Long): PosterEntity?

    @Query("SELECT * FROM PosterEntity")
    fun getPosters(): List<PosterEntity>
}
