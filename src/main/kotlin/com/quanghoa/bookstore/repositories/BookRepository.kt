package com.quanghoa.bookstore.repositories

import com.quanghoa.bookstore.models.Book
import org.springframework.data.repository.PagingAndSortingRepository

interface BookRepository: PagingAndSortingRepository<Book, Int>