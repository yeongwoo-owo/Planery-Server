package com.planery.application

import com.planery.domain.user.UserRepository
import com.planery.domain.user.find
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository
) {
    fun find(userId: Long) = userRepository.find(userId)
        ?: throw UserNotExistException(key = "id", value = userId.toString(), message = "회원이 존재하지 않습니다.")
}
