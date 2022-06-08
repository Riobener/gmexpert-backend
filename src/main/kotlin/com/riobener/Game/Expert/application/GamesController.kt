package com.riobener.Game.Expert.application

import com.riobener.Game.Expert.domain.entities.JpaUser
import com.riobener.Game.Expert.domain.repositories.FavoriteRepository
import com.riobener.Game.Expert.infrastructure.dto.GameDetailsResponse
import com.riobener.Game.Expert.infrastructure.dto.GameResponse
import com.riobener.Game.Expert.infrastructure.dto.SimpleResponse
import com.riobener.Game.Expert.infrastructure.services.FavoriteService
import com.riobener.Game.Expert.infrastructure.services.UserService
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate


@RestController

@RequestMapping("/api")
class GamesController(
    @Autowired
    private val favoriteService: FavoriteService
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
    fun getGamesBySearch(
        @RequestParam(name = "query") query: String,
        @RequestParam(name = "page_size") pageSize: String
    ): GameResponse {
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
        val principal = SecurityContextHolder.getContext().authentication.principal as JpaUser
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
        val inFavorite = favoriteService.isGameInFavorite(gameId,principal.id.toString())
        return GameDetailsResponse(
            details = format.decodeFromString(details!!),
            images = format.decodeFromString(screenshots!!),
            inFavorite = inFavorite
        )
    }

    @PostMapping("/games/favorite", "application/json")
    fun addToFavorite(@RequestParam gameId: String,@RequestParam title: String,@RequestParam screenshot: String): ResponseEntity<*>? {
        val principal = SecurityContextHolder.getContext().authentication.principal as JpaUser
        val gameScreenshot = screenshot ?: ""
        val result = favoriteService.addToFavorite(gameId,principal.id.toString(),gameScreenshot,title)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/games/favorite/all", "application/json")
    fun getAllFavorites(): ResponseEntity<*>? {
        val principal = SecurityContextHolder.getContext().authentication.principal as JpaUser
        val result = favoriteService.findUserFavorites(principal.id.toString())
        return ResponseEntity.ok(result)
    }


}