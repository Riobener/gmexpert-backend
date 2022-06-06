package com.riobener.Game.Expert.infrastructure.persistence

import com.riobener.Game.Expert.domain.entities.JpaUser
import com.riobener.Game.Expert.domain.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface UserJpaRepository : JpaRepository<JpaUser, UUID> {
    fun findByUsername(username: String): JpaUser?
}

@Component
class UserRepositoryImpl(
    @Autowired
    private val jpaUserRepository: UserJpaRepository
) : UserRepository {
    override fun save(jpaUser: JpaUser): JpaUser {
        return jpaUserRepository.save(jpaUser)
    }

    override fun findByUsername(username: String): JpaUser? {
        return jpaUserRepository.findByUsername(username)
    }
}