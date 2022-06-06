package com.riobener.Game.Expert.infrastructure.dto

import kotlinx.serialization.Serializable

@Serializable
data class GameResponse(
    val count: Int,
    val next: String,
    val previous: String? = null,
    val results: List<Game>
)
@Serializable
data class Game(
    val id: Int,
    val slug: String,
    val name: String,
    val tba: Boolean,
    val background_image: String,
    val rating: Double
)
