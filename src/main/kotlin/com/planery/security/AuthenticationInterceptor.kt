package com.planery.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthenticationInterceptor(private val tokenProvider: TokenProvider) : HandlerInterceptor {
    companion object {
        private const val LOGIN_ID = "loginId"
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val cookie = request.cookies?.find { it.name == TokenProvider.AUTH_TOKEN }
            ?: throw InvalidAuthCookieException(message = "인증 쿠기가 존재하지 않습니다.")
        if (!tokenProvider.isValidToken(cookie.value)) throw InvalidAuthCookieException(message = "잘못된 인증 쿠기입니다.")
        request.setAttribute(LOGIN_ID, tokenProvider.getSubject(cookie.value))
        return true
    }
}
