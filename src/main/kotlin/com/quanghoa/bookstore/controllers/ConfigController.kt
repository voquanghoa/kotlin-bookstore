package com.quanghoa.bookstore.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ConfigController {
    @GetMapping("csrf")
    fun csrf(): String{
        return "csrf"
    }
}