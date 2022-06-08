package com.riobener.Game.Expert.infrastructure.security

import com.riobener.Game.Expert.infrastructure.security.jwt.JwtRequestFilter
import com.riobener.Game.Expert.infrastructure.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter





@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    @Autowired
    private val userService: UserService,
    @Autowired
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    @Autowired
    private val jwtRequestFilter: JwtRequestFilter
) : WebSecurityConfigurerAdapter() {


    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/user/auth").permitAll()
            .antMatchers("/api/user/register").permitAll()
            .antMatchers("/api/games").permitAll()
            .antMatchers("/api/games/search").permitAll()
            .antMatchers("/api/games/details").permitAll()
            .anyRequest().authenticated()
            .and()
            .exceptionHandling().and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Throws(java.lang.Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(daoAuthenticationProvider())
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider? {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder)
        authenticationProvider.setUserDetailsService(userService)
        return authenticationProvider
    }

    @Bean
    @Throws(java.lang.Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

    @Autowired
    @Throws(java.lang.Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService<UserDetailsService>(userService)
    }
}