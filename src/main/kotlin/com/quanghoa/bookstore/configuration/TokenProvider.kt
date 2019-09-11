package com.quanghoa.bookstore.configuration

import com.quanghoa.bookstore.configuration.Constants.ACCESS_TOKEN_VALIDITY_SECONDS
import com.quanghoa.bookstore.configuration.Constants.AUTHORITIES_KEY
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.Authentication
import java.util.*

@Component
@Configuration
class TokenProvider(@Value("jwt.key") var signingKey: String) {

    fun getUsernameFromToken(token: String): String {
        return getClaimFromToken(token){it.subject}
    }

    fun getExpirationDateFromToken(token: String): Date {
        return getClaimFromToken(token) { it.expiration }
    }

    fun <T> getClaimFromToken(token: String, claimsResolver: ((Claims) -> T)): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.invoke(claims)
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .body
    }
    private fun isTokenExpired(token: String): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    fun generateToken(authentication: Authentication): String {
        val authorities = authentication
                .authorities
                .joinToString(",") { it.authority }

        return Jwts.builder()
                .setSubject(authentication.name)
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .compact()
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails.username && (!isTokenExpired(token))
    }

    fun getAuthentication(token: String, existingAuth: Authentication, userDetails: UserDetails):
            UsernamePasswordAuthenticationToken {

        val jwtParser = Jwts.parser().setSigningKey(signingKey)

        val claimsJws = jwtParser.parseClaimsJws(token)

        val claims = claimsJws.body

        val authorities = claims[AUTHORITIES_KEY]
                .toString().split(",".toRegex())
                .dropLastWhile { it.isEmpty() }
                .map{ SimpleGrantedAuthority(it) }
                .toList()

        return UsernamePasswordAuthenticationToken(userDetails, null, authorities)
    }
}