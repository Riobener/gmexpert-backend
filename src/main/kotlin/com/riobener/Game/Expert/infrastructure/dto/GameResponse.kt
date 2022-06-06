package com.riobener.Game.Expert.infrastructure.dto

import kotlinx.serialization.Serializable

@Serializable
data class GameResponse(
    val results: List<Game>
)
@Serializable
data class Game(
    val id: Int,
    val name: String,
    val background_image: String,
    val rating: Double
)
