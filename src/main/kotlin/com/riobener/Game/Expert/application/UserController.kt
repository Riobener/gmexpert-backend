package com.riobener.Game.Expert.application

import com.riobener.Game.Expert.domain.entities.JpaUser
import com.riobener.Game.Expert.infrastructure.dto.AuthenticationRequest
import com.riobener.Game.Expert.infrastructure.dto.AuthenticationResponse
import com.riobener.Game.Expert.infrastructure.dto.GameResponse
import com.riobener.Game.Expert.infrastructure.dto.SimpleResponse
import com.riobener.Game.Expert.infrastructure.security.JwtUtil
import com.riobener.Game.Expert.infrastructure.services.UserService
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate


@RestController

@RequestMapping("/api")
class UserController(
    @Autowired
    private val userService: UserService,
    @Autowired
    private val authenticationManager: AuthenticationManager,
    @Autowired
    private val jwtTokenUtil: JwtUtil
) {
    @GetMapping("/hello")
    fun helloUser(): ResponseEntity<*>? {
        return ResponseEntity.ok().body(SimpleResponse("Welcome to games service Api!"))
    }

    @PostMapping("/user/register", "application/json")
    fun registerUser(@RequestBody jpaUser: JpaUser): ResponseEntity<*>? {
        userService.createUser(jpaUser.username, jpaUser.password)
        return ResponseEntity.ok().body(SimpleResponse("OK"))
    }

    @RequestMapping(value = ["/user/auth"], method = [RequestMethod.POST])
    @Throws(Exception::class)
    fun createAuthenticationToken(@RequestBody authenticationRequest: AuthenticationRequest): ResponseEntity<*>? {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    authenticationRequest.username,
                    authenticationRequest.password
                )
            )
        } catch (ex: BadCredentialsException) {
            throw Exception("Incorrect username or password", ex)
        }
        val userDetails = userService
            .loadUserByUsername(authenticationRequest.username)
        val jwt: String = jwtTokenUtil.generateToken(userDetails)
        return ResponseEntity.ok<Any>(AuthenticationResponse(jwt))
    }
}