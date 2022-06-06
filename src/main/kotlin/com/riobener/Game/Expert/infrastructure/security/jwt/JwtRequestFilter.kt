package com.riobener.Game.Expert.infrastructure.security.jwt

import com.riobener.Game.Expert.infrastructure.security.JwtUtil
import com.riobener.Game.Expert.infrastructure.services.UserService
import io.jsonwebtoken.SignatureException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter : OncePerRequestFilter() {
    @Autowired
    private val userDetailsService: UserService? = null

    @Autowired
    private val jwtUtil: JwtUtil? = null

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

            val authorizationHeader = request.getHeader("Authorization")
            var username: String? = null
            var jwt: String? = null
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7)
                try{
                    username = jwtUtil!!.extractUsername(jwt)
                }catch(ex: SignatureException){ }
            }
            if (username != null && SecurityContextHolder.getContext().authentication == null) {
                val userDetails = userDetailsService!!.loadUserByUsername(username!!)
                if (jwtUtil!!.validateToken(jwt, userDetails)) {
                    val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.authorities
                    )
                    usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
                }
            }
            filterChain.doFilter(request, response)

    }
}