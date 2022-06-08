package com.riobener.Game.Expert.domain.entities

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class JpaFavorite(
    @Id
    val id: UUID?,
    val userId: String,
    val gameId: String,
    val gameTitle: String,
    val screenshot: String
)