package com.quanghoa.bookstore.exceptions

import java.lang.RuntimeException

class BadRequestException(message: String) : RuntimeException(message)