package com.quanghoa.bookstore.repositories

import com.quanghoa.bookstore.models.dao.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int>{
    fun findByUsername(username: String): User?
}