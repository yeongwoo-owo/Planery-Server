package com.planery.api

import com.planery.application.UserAuthenticateService
import com.planery.security.TokenProvider
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    private val authenticateService: UserAuthenticateService,
    private val tokenProvider: TokenProvider
) {
    @PostMapping("/generate")
    fun generate(response: HttpServletResponse): ResponseEntity<CookieResponse> {
        val user = authenticateService.generate()
        val token = tokenProvider.createToken(user.id.toString())
        val cookie = Cookie(TokenProvider.AUTH_TOKEN, token)
        cookie.path = "/api"
        response.addCookie(cookie)
        return ResponseEntity.ok(CookieResponse(name = cookie.name, value = cookie.value))
    }

    @GetMapping("/validate")
    fun validate(@RequestAttribute loginId: Long): ResponseEntity<Void> {
        authenticateService.validate(loginId)
        return ResponseEntity.noContent().build()
    }
}
