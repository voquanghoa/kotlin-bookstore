package com.quanghoa.bookstore.repositories

import com.quanghoa.bookstore.models.dao.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Int>{
    fun findByName(name: String): Role?
}