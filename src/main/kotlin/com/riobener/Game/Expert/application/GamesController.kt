package com.riobener.Game.Expert.application

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
}