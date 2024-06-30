package com.example.middleend

import com.example.middleend.pet.service.AuthService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuthenticationFilter(
    private val authService: AuthService
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authorizationHeader = request.getHeader("Authorization")
        if (authorizationHeader != null) {
            val validationResponse = authService.validateToken(authorizationHeader)

            if (validationResponse.statusCode.is2xxSuccessful) {
                val user = validationResponse.body

                request.setAttribute("authenticatedUser", user)

                filterChain.doFilter(request, response)
            } else {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
            }
        } else {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
        }
    }
}
