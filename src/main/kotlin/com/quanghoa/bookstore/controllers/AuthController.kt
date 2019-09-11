package com.quanghoa.bookstore.controllers

import com.quanghoa.bookstore.configuration.TokenProvider
import com.quanghoa.bookstore.models.dto.Login
import com.quanghoa.bookstore.models.dto.Token
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception


@RestController
@RequestMapping("/api/auth")
class AuthController(
        @Autowired val authenticationManager: AuthenticationManager,
        @Autowired val jwtTokenUtil: TokenProvider
) {

    @PostMapping
    fun login(@RequestBody login: Login): Token {
        try {
            val authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                            login.username,
                            login.password
                    )
            )
            SecurityContextHolder.getContext().authentication = authentication
            val token = jwtTokenUtil.generateToken(authentication)
            return Token(token)
        }catch (ex: Exception){
            return Token("112")
        }
    }
}