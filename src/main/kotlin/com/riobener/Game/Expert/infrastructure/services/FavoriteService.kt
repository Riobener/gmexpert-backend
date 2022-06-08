package com.riobener.Game.Expert.infrastructure.services

import com.riobener.Game.Expert.domain.entities.JpaFavorite
import com.riobener.Game.Expert.domain.entities.JpaUser
import com.riobener.Game.Expert.domain.repositories.FavoriteRepository
import com.riobener.Game.Expert.domain.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class FavoriteService(
    @Autowired
    private val favoriteRepository: FavoriteRepository,
){
    fun addToFavorite(gameId: String, userId: String, screenshot: String, title: String): JpaFavorite{
        return favoriteRepository.save(JpaFavorite(UUID.randomUUID(),userId,gameId,title,screenshot))
    }

    fun findUserFavorites(userId: String): List<JpaFavorite>?{
        val result = favoriteRepository.findUserFavorites(userId)

        return if(result.isNullOrEmpty()){
            listOf<JpaFavorite>()
        }else{
            result
        }
    }

}