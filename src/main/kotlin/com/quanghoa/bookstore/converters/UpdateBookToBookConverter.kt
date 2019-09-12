package com.quanghoa.bookstore.converters

import com.quanghoa.bookstore.models.dao.Book
import com.quanghoa.bookstore.models.dto.UpdateBook
import org.springframework.stereotype.Component

@Component
class UpdateBookToBookConverter : Converter<UpdateBook, Book>{
    override fun convert(source: UpdateBook): Book {
        return Book(source.id, source.title, source.year)
    }
}