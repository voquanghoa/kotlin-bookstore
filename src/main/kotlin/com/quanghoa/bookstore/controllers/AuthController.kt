package com.quanghoa.bookstore.controllers

import com.quanghoa.bookstore.configuration.Constants
import com.quanghoa.bookstore.configuration.TokenProvider
import com.quanghoa.bookstore.converters.Converter
import com.quanghoa.bookstore.exceptions.BadRequestException
import com.quanghoa.bookstore.models.dao.User
import com.quanghoa.bookstore.models.dto.Login
import com.quanghoa.bookstore.models.dto.Signup
import com.quanghoa.bookstore.models.dto.Token
import com.quanghoa.bookstore.repositories.RoleRepository
import com.quanghoa.bookstore.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@RestController
@RequestMapping("/api/auth")
class AuthController(
        @Autowired val authenticationManager: AuthenticationManager,
        @Autowired val jwtTokenUtil: TokenProvider,
        @Autowired val converter: Converter<Signup, User>,
        @Autowired val roleRepository: RoleRepository,
        @Autowired val userRepository: UserRepository
) {

    @PostMapping
    fun login(@RequestBody login: Login): Token {
        val authentication = authenticationManager.authenticate(login.asObject())
        SecurityContextHolder.getContext().authentication = authentication

        return Token(jwtTokenUtil.generateToken(authentication))
    }

    @PostMapping("signup")
    fun signup(@Valid @RequestBody signup: Signup){

        if(userRepository.findByUsername(signup.username) != null){
            throw BadRequestException("The username ${signup.username} is already exist.")
        }

        val user = converter.convert(signup)

        user.roles.add(roleRepository.findByName(Constants.ROLE_MEMBER)!!)
        user.password = BCryptPasswordEncoder().encode(user.password)

        userRepository.save(user)
    }
}