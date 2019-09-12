package com.quanghoa.bookstore.converters

import com.quanghoa.bookstore.models.dao.User
import com.quanghoa.bookstore.models.dto.Signup
import org.springframework.stereotype.Component

@Component
class SignupToUserConverter : Converter<Signup, User>{

    override fun convert(source: Signup): User {
        return User(0, source.username, source.password, source.fullName)
    }

}