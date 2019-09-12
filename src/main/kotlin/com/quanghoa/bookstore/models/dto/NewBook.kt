package com.quanghoa.bookstore.models.dto

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class NewBook (
        @field:NotBlank val title: String,
        @field:Max(3000) @field:Min(1900) val year: Int
)