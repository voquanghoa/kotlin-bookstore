package com.quanghoa.bookstore.models.dao

import javax.persistence.*

@Entity
data class Role (
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int,

        @Column(nullable = false)
        var name: String,

        var description: String
){
        constructor():this(0, "", "")
}