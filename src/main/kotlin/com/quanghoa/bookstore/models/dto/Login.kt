package com.quanghoa.bookstore.models.dto

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

data class Login(
        val username: String,
        val password: String
){
    fun asObject() = UsernamePasswordAuthenticationToken(username, password)
}