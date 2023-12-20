package com.planery.application

import com.planery.domain.calendar.Calendar
import com.planery.domain.calendar.CalendarRepository
import com.planery.domain.calendar.find
import com.planery.domain.calendar.findAllByUserId
import com.planery.domain.user.User
import com.planery.domain.user.UserRepository
import com.planery.domain.user.find
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@Service
@Transactional
class CalendarService(
    private val userRepository: UserRepository,
    private val calendarRepository: CalendarRepository
) {
    fun create(userId: Long, name: String, colorCode: String): Calendar {
        val user = userRepository.find(userId)
            ?: throw UserNotExistException(key = "id", value = userId.toString(), message = "회원이 존재하지 않습니다.")
        return calendarRepository.save(Calendar(owner = user, name = name, colorCode = colorCode))
            .also { logger.info { "캘린더 생성: ${it.id}" } }
    }

    fun findByUserId(userId: Long): List<Calendar> {
        return calendarRepository.findAllByUserId(userId)
    }

    fun update(userId: Long, calendarId: Long, name: String, colorCode: String): Calendar {
        val calendar = validateAndGetCalendar(userId, calendarId)
        calendar.update(name = name, colorCode = colorCode)
        return calendar.also { logger.info { "캘린더 수정: ${it.id}" } }
    }

    fun delete(userId: Long, calendarId: Long) {
        val calendar = validateAndGetCalendar(userId, calendarId)
            .also { logger.info { "캘린더 삭제: ${it.id}" } }
        calendarRepository.delete(calendar)
    }

    private fun validateAndGetCalendar(userId: Long, calendarId: Long): Calendar {
        val user = userRepository.find(userId)
            ?: throw UserNotExistException(key = "id", value = userId.toString(), message = "회원이 존재하지 않습니다.")
        val calendar = calendarRepository.find(calendarId)
            ?: throw CalendarNotExistException(key = "id", value = calendarId.toString(), message = "캘린더가 존재하지 않습니다.")
        validate(user = user, calendar = calendar)
        return calendar
    }

    private fun validate(user: User, calendar: Calendar) {
        if (calendar.owner != user)
            throw CalendarForbiddenException(
                userId = user.id,
                key = "calendarId",
                value = calendar.id.toString()
            )
    }
}
