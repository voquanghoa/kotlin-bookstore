package com.quanghoa.bookstore.exceptions

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.ServletWebRequest

data class ErrorModel(
    val message: String,
    val path: String
)

@ControllerAdvice
class BadCredentialsExceptionHandler {

    @ExceptionHandler(BadCredentialsException::class)
    fun handleException(ex: BadCredentialsException, request: ServletWebRequest): ResponseEntity<ErrorModel> {
        return ResponseEntity(ErrorModel(ex.message!!, request.request.requestURI), HttpStatus.BAD_REQUEST)
    }
}