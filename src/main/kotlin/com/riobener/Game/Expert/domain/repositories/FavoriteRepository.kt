package com.riobener.Game.Expert.domain.repositories

import com.riobener.Game.Expert.domain.entities.JpaFavorite

interface FavoriteRepository {
    fun save(jpaFavorite: JpaFavorite): JpaFavorite
    fun findUserFavorites(userId: String): List<JpaFavorite>?
    fun getGameInFavorites(gameId: String, userId: String): JpaFavorite?
}