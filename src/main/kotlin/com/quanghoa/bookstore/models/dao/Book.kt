package com.quanghoa.bookstore.models.dao

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Book(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,
        val title: String,
        val year: Int
){
        constructor():this(0, "", 0)
}