package com.planery.security

class InvalidAuthCookieException(message: String? = null): RuntimeException(message) {
    val content = InvalidAuthCookieContent(message ?: "인증 오류")
}

data class InvalidAuthCookieContent(val message: String)
