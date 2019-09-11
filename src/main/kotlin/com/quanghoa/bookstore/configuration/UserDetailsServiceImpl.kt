package com.quanghoa.bookstore.configuration

import com.quanghoa.bookstore.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component


@Component(value="userDetailsService")
class UserDetailsServiceImpl(@Autowired private val userRepository: UserRepository) : UserDetailsService{

    override fun loadUserByUsername(username: String?): UserDetails {

        if(username == null){
            throw UsernameNotFoundException("Username is empty")
        }

        val user = userRepository.findByUsername(username) ?: throw UsernameNotFoundException("User not found")

        val grantedAuthorities = user.roles.map { SimpleGrantedAuthority(it.name) }

        return org.springframework.security.core.userdetails.User(
                user.username, user.password, grantedAuthorities)
    }

}