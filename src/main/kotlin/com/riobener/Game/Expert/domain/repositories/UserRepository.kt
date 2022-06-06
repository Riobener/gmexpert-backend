package com.riobener.Game.Expert.domain.repositories

import com.riobener.Game.Expert.domain.entities.JpaUser

interface UserRepository {
    fun save(jpaUser: JpaUser): JpaUser
    fun findByUsername(username: String): JpaUser?
}