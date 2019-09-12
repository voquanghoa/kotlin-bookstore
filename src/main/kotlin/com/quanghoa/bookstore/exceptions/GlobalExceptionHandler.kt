package com.quanghoa.bookstore.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.ServletWebRequest


@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(BadCredentialsException::class)
    fun handleException(ex: BadCredentialsException, request: ServletWebRequest): ResponseEntity<ErrorModel> {
        return ResponseEntity(ErrorModel(ex.message!!, request.request.requestURI), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleException(ex: BadRequestException, request: ServletWebRequest): ResponseEntity<ErrorModel> {
        return ResponseEntity(ErrorModel(ex.message!!, request.request.requestURI), HttpStatus.BAD_REQUEST)
    }
}