package com.quanghoa.bookstore.configuration

object Constants {
    const val ACCESS_TOKEN_VALIDITY_SECONDS = (30 * 24 * 60 * 60).toLong()
    const val TOKEN_PREFIX = "Bearer "
    const val HEADER_STRING = "Authorization"
    const val AUTHORITIES_KEY = "scopes"

    const val ROLE_ADMIN = "ROLE_ADMIN"
    const val ROLE_MEMBER = "ROLE_MEMBER"
}