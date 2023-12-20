package com.planery.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class TokenProvider(
    init: InitKey,
) {
    private val key: SecretKey = Keys.hmacShaKeyFor(init.key.toByteArray())

    companion object {
        const val AUTH_TOKEN = "X_AUTH_TOKEN"
    }

    fun createToken(payload: String): String {
        val claims: Claims = Jwts.claims().setSubject(payload)
        val now = Date()
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .signWith(key)
            .compact()
    }

    fun getSubject(token: String): String = getClaims(token).body.subject

    fun isValidToken(token: String): Boolean {
        return try {
            getClaims(token)
            true
        } catch (e: JwtException) {
            false
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    private fun getClaims(token: String) = Jwts.parserBuilder()
        .setSigningKey(key.encoded)
        .build()
        .parseClaimsJws(token)
}

@ConfigurationProperties("jwt")
data class InitKey(val key: String)