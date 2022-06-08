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
}