package com.woosuk.disenycompose.data.persistence.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.woosuk.disenycompose.domain.model.Poster

@Entity
data class PosterEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val release: String,
    val playtime: String,
    val description: String,
    val plot: String,
    val poster: String,
    val gif: String,
)

fun PosterEntity.toPoster() = Poster(
    id = id,
    name = name,
    release = release,
    playtime = playtime,
    description = description,
    plot = plot,
    poster = poster,
    gif = gif,
)

fun PosterEntity.toPosterEntity() = PosterEntity(
    id = id,
    name = name,
    release = release,
    playtime = playtime,
    description = description,
    plot = plot,
    poster = poster,
    gif = gif,
)
