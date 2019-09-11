package com.quanghoa.bookstore.configuration

import com.quanghoa.bookstore.models.dao.Role
import com.quanghoa.bookstore.models.dao.User
import com.quanghoa.bookstore.repositories.RoleRepository
import com.quanghoa.bookstore.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
@Configuration
class DataSeedingListener(
        @Autowired val userRepository: UserRepository,
        @Autowired val roleRepository: RoleRepository
) : ApplicationListener<ContextRefreshedEvent> {

    private fun addRoleIfMissing(name: String, description: String){
        if (roleRepository.findByName(name) == null) {
            roleRepository.save(Role(0, name, description))
        }
    }

    private fun addUserIfMissing(username: String,
                                 password: String,
                                 fullName: String,
                                 vararg roles: String) {
        if (userRepository.findByUsername(username) == null) {
            val encodedPassword = BCryptPasswordEncoder().encode(password)
            val user = User(0, username, fullName, encodedPassword)

            for (role in roles) {
                user.roles.add(roleRepository.findByName(role)!!)
            }

            userRepository.save(user)
        }
    }

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        addRoleIfMissing("ROLE_ADMIN", "Administrators")
        addRoleIfMissing("ROLE_MEMBER", "Members")

        addUserIfMissing("user", "456", "User", "ROLE_MEMBER")
        addUserIfMissing("admin", "1234", "Admin", "ROLE_MEMBER", "ROLE_ADMIN")
    }
}