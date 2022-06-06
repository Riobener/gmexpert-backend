package com.riobener.Game.Expert.infrastructure.services

import com.riobener.Game.Expert.domain.entities.JpaUser
import com.riobener.Game.Expert.domain.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserService(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : UserDetailsService {
    fun createUser(username: String, password: String) {
        val encodedPassword = bCryptPasswordEncoder.encode(password)
        userRepository.save(JpaUser(UUID.randomUUID(), username = username, password = encodedPassword))
    }

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByUsername(username) as UserDetails
    }
}