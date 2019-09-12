package com.quanghoa.bookstore.configuration

import com.quanghoa.bookstore.configuration.Constants.HEADER_STRING
import com.quanghoa.bookstore.configuration.Constants.TOKEN_PREFIX
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.filter.OncePerRequestFilter
import javax.annotation.Resource
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource


class JwtAuthenticationFilter(
        @Resource val userDetailsService: UserDetailsService,
        @Autowired val jwtTokenUtil: TokenProvider
) : OncePerRequestFilter(){

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val header = request.getHeader(HEADER_STRING)
        var authToken = ""
        var username: String? = ""

        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX, "")
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken)
            } catch (e: IllegalArgumentException) {
                logger.error("an error occurred during getting username from token", e)
            } catch (e: ExpiredJwtException) {
                logger.warn("the token is expired and not valid anymore", e)
            }
        }

        if (username != null && username.any() && SecurityContextHolder.getContext().authentication == null) {

            val userDetails = userDetailsService.loadUserByUsername(username)

            if (userDetails != null) {

                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    val authentication = jwtTokenUtil.getAuthentication(authToken, SecurityContextHolder.getContext().authentication, userDetails)
                    authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                    logger.info("authenticated user $username, setting security context")
                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        }

        filterChain.doFilter(request, response)
    }

}