package com.quanghoa.bookstore.configuration

object Constants {
    val ACCESS_TOKEN_VALIDITY_SECONDS = (30 * 24 * 60 * 60).toLong()
    val TOKEN_PREFIX = "Bearer "
    val HEADER_STRING = "Authorization"
    val AUTHORITIES_KEY = "scopes"
}