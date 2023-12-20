package com.planery.application

import com.planery.domain.user.User
import com.planery.domain.user.UserRepository
import com.planery.security.InvalidAuthCookieException
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@Service
@Transactional
class UserAuthenticateService(
    private val userRepository: UserRepository
) {
    fun generate(): User {
        return userRepository.save(User(name = createUsername()))
            .also { logger.info { "회원 생성: ${it.id}" } }
    }

    private fun createUsername(): String {
        val charset = ('a'..'z') + ('A'..'Z') + (0..9)
        return "User#" + (1..6)
            .map { charset.random() }
            .joinToString("")
    }

    fun validate(userId: Long) {
        if (!userRepository.existsById(userId)) {
            throw InvalidAuthCookieException()
        }
    }
}
