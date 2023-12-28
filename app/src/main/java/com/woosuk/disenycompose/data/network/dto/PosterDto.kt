package com.woosuk.disenycompose.data.network.dto

import com.woosuk.disenycompose.data.persistence.entitiy.PosterEntity
import com.woosuk.disenycompose.domain.model.Poster
import kotlinx.serialization.Serializable

@Serializable
data class PosterDto(
    val id: Long,
    val name: String,
    val release: String,
    val playtime: String,
    val description: String,
    val plot: String,
    val poster: String,
    val gif: String,
)

fun PosterDto.toPoster() = Poster(
    id = id,
    name = name,
    release = release,
    playtime = playtime,
    description = description,
    plot = plot,
    poster = poster,
    gif = gif,
)

fun PosterDto.toEntity() = PosterEntity(
    id = id,
    name = name,
    release = release,
    playtime = playtime,
    description = description,
    plot = plot,
    poster = poster,
    gif = gif,
)
