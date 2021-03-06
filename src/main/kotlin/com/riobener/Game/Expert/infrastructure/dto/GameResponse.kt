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
    val background_image: String? = null,
    val rating: Double
)

@Serializable
data class GameDetails(
    val id: Int,
    val name: String,
    val description: String,
    val released: String,
    val background_image: String? = null,
    val rating: Double,
    val metacritic: Int? = null,
    val website: String? = null,
    val metacritic_url: String? = null,
)

@Serializable
data class GameScreenshots(
    val results: List<Screenshot>
)

@Serializable
data class Screenshot(
    val image: String
)

@Serializable
data class GameDetailsResponse(
    val details: GameDetails,
    val images: GameScreenshots,
    val inFavorite: Boolean
)
