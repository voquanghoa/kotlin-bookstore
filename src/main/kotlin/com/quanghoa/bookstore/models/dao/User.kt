package com.quanghoa.bookstore.models.dao

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "Users")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int,
        var username: String,
        var password: String,
        var fullName: String,
        var valid: Boolean = true,
        var registrationDate: Date = Date(),
        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "UserRole",
                joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
        var roles: MutableList<Role> = mutableListOf()
){
        constructor():this(0, "", "", "", true, Date(), mutableListOf())
}