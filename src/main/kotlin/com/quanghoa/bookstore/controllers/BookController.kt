package com.quanghoa.bookstore.controllers

import com.quanghoa.bookstore.models.Book
import com.quanghoa.bookstore.repositories.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/books")
class BookController(
        @Autowired
        var bookRepository: BookRepository
) {

    @GetMapping
    fun all(): List<Book>{
        return bookRepository.findAll().toList()
    }
}