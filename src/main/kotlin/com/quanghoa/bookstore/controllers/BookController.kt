package com.quanghoa.bookstore.controllers

import com.quanghoa.bookstore.converters.Converter
import com.quanghoa.bookstore.exceptions.BadRequestException
import com.quanghoa.bookstore.models.dao.Book
import com.quanghoa.bookstore.models.dto.NewBook
import com.quanghoa.bookstore.models.dto.UpdateBook
import com.quanghoa.bookstore.repositories.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.DeleteMapping


@RestController
@RequestMapping("/api/books")
class BookController(
        @Autowired var bookRepository: BookRepository,
        @Autowired var bookConverter: Converter<NewBook, Book>,
        @Autowired var updateBookToBookConverter: Converter<UpdateBook, Book>
) {

    @GetMapping
    fun all(): List<Book>{
        return bookRepository.findAll().toList()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): Book{
        val optBook = bookRepository.findById(id)
        if(optBook.isPresent){
            return optBook.get()
        }
        throw BadRequestException("No book with id $id")
    }

    @PostMapping
    fun post(@Valid @RequestBody newBook: NewBook): Book{
        return bookRepository.save(bookConverter.convert(newBook))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        val book = bookRepository.findById(id)
        if(book.isPresent){
            bookRepository.delete(book.get())
        }else{
            throw BadRequestException("No book with id $id")
        }
    }

    @PutMapping
    fun update(@Valid @RequestBody updateBook: UpdateBook){
        val book = bookRepository.findById(updateBook.id)

        if(book.isPresent){
            bookRepository.save(updateBookToBookConverter.convert(updateBook))
        }else{
            throw BadRequestException("No book with id ${updateBook.id}")
        }
    }
}