package com.planery.api

import com.planery.application.ForbiddenException
import com.planery.application.NotExistException
import com.planery.security.InvalidAuthCookieContent
import com.planery.security.InvalidAuthCookieException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

private val logger = KotlinLogging.logger {}

@RestControllerAdvice
class ApiControllerAdvice {
    @ExceptionHandler(NotExistException::class)
    fun notExistException(e: NotExistException): ResponseEntity<NotExistException.NotExistContent> {
        logger.info { e }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.content)
    }

    @ExceptionHandler(InvalidAuthCookieException::class)
    fun invalidAuthCookieException(e: InvalidAuthCookieException): ResponseEntity<InvalidAuthCookieContent> {
        logger.info { e }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.content)
    }

    @ExceptionHandler(ForbiddenException::class)
    fun forbiddenException(e: ForbiddenException): ResponseEntity<ForbiddenException.ForbiddenContent> {
        logger.info { e }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.content)
    }
}