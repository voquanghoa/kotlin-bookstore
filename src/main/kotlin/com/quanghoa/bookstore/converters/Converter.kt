package com.quanghoa.bookstore.converters

interface Converter<I, O> {

    fun convert(source: I): O

    fun convert(sources: List<I>): List<O>{
        return sources.map { convert(it) }
    }
}