package com.riobener.Game.Expert.infrastructure.persistence

import com.riobener.Game.Expert.domain.entities.JpaFavorite
import com.riobener.Game.Expert.domain.entities.JpaUser
import com.riobener.Game.Expert.domain.repositories.FavoriteRepository
import com.riobener.Game.Expert.domain.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FavoriteJpaRepository : JpaRepository<JpaFavorite, UUID> {
    fun findAllByUserId(userId: String): List<JpaFavorite>?
    fun findByGameIdAndUserId(gameId: String, userId: String): JpaFavorite?
    fun deleteByGameIdAndUserId(gameId: String,userId: String): JpaFavorite
}

@Component
class FavoriteRepositoryImpl(
    @Autowired
    private val favoriteJpaRepository: FavoriteJpaRepository
) : FavoriteRepository {

    override fun save(jpaFavorite: JpaFavorite): JpaFavorite {
        return favoriteJpaRepository.save(jpaFavorite)
    }

    override fun findUserFavorites(userId: String): List<JpaFavorite>? {
        return favoriteJpaRepository.findAllByUserId(userId)
    }

    override fun getGameInFavorites(gameId: String, userId: String): JpaFavorite? {
        return favoriteJpaRepository.findByGameIdAndUserId(gameId,userId)
    }

    override fun deleteFavorite(gameId: String, userId: String): JpaFavorite {
        return favoriteJpaRepository.deleteByGameIdAndUserId(gameId,userId)
    }
}