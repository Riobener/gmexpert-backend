package com.riobener.Game.Expert.application

import com.riobener.Game.Expert.infrastructure.dto.GameDetailsResponse
import com.riobener.Game.Expert.infrastructure.dto.GameResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate


@RestController

@RequestMapping("/api")
class GamesController(
) {
    @Value("\${apiKey}")
    private val apiKey: String? = null

    @GetMapping("/games")
    fun getGames(@RequestParam page: String, @RequestParam(name = "page_size") pageSize: String): GameResponse {
        val format = Json {
            coerceInputValues = true
            ignoreUnknownKeys = true
        }
        val url =
            "https://api.rawg.io/api/games?key=$apiKey&page=$page&page_size=$pageSize"
        val restTemplate = RestTemplate()
        val result: String? = restTemplate.getForObject(url, String::class.java)
        return format.decodeFromString(result!!)
    }

    @GetMapping("/games/search")
    fun getGamesBySearch(@RequestParam(name = "query") query: String, @RequestParam(name = "page_size") pageSize: String): GameResponse {
        val format = Json {
            coerceInputValues = true
            ignoreUnknownKeys = true
        }
        val url =
            "https://api.rawg.io/api/games?key=$apiKey&search=$query&page_size=$pageSize"
        val restTemplate = RestTemplate()
        val result: String? = restTemplate.getForObject(url, String::class.java)
        return format.decodeFromString(result!!)
    }

    @GetMapping("/games/details")
    fun getGameDetails(@RequestParam gameId: String): GameDetailsResponse {
        val format = Json {
            coerceInputValues = true
            ignoreUnknownKeys = true
        }
        val urlDetails =
            "https://api.rawg.io/api/games/${gameId}?key=$apiKey"
        val urlScreenshots =
            "https://api.rawg.io/api/games/$gameId/screenshots?key=$apiKey"
        val restTemplate = RestTemplate()
        val details: String? = restTemplate.getForObject(urlDetails, String::class.java)
        val screenshots: String? = restTemplate.getForObject(urlScreenshots, String::class.java)
        return GameDetailsResponse(
            details = format.decodeFromString(details!!),
            images = format.decodeFromString(screenshots!!)
        )
    }
}