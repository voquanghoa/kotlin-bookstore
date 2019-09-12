package com.quanghoa.bookstore.converters

import com.quanghoa.bookstore.models.dao.Book
import com.quanghoa.bookstore.models.dto.NewBook
import org.springframework.stereotype.Component

@Component
class NewBookToBookConverter : Converter<NewBook, Book>{
    override fun convert(source: NewBook): Book {
        return Book(0, source.title, source.year)
    }
}