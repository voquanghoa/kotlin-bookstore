package com.quanghoa.bookstore.controllers

import com.quanghoa.bookstore.configuration.TokenProvider
import com.quanghoa.bookstore.models.dto.Login
import com.quanghoa.bookstore.models.dto.Token
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
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
        val authentication = authenticationManager.authenticate(login.asObject())
        SecurityContextHolder.getContext().authentication = authentication

        return Token(jwtTokenUtil.generateToken(authentication))
    }
}