package com.planery.application

import com.planery.domain.calendar.Calendar
import com.planery.domain.calendar.CalendarRepository
import com.planery.domain.calendar.find
import com.planery.domain.event.Duration
import com.planery.domain.event.Event
import com.planery.domain.event.EventRepository
import com.planery.domain.event.EventType
import com.planery.domain.event.find
import com.planery.domain.event.findByUserId
import com.planery.domain.user.User
import com.planery.domain.user.UserRepository
import com.planery.domain.user.find
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

@Service
@Transactional
class EventService(
    private val userRepository: UserRepository,
    private val calendarRepository: CalendarRepository,
    private val eventRepository: EventRepository
) {
    fun create(
        userId: Long,
        calendarId: Long,
        eventType: EventType,
        duration: Duration,
        name: String
    ): Event {
        val user = userRepository.find(userId)
            ?: throw UserNotExistException(key = "id", value = userId.toString(), message = "회원이 존재하지 않습니다.")
        val calendar = calendarRepository.find(calendarId)
            ?: throw CalendarNotExistException(key = "id", value = calendarId.toString(), message = "캘린더가 존재하지 않습니다.")
        validate(user,calendar)
        return eventRepository.save(
            Event(
                owner = user,
                calendar = calendar,
                duration = duration,
                type = eventType,
                name = name
            )
        ).also { logger.info { "이벤트 생성: ${it.id}" } }
    }

    fun findByUserId(userId: Long): List<Event> = eventRepository.findByUserId(userId)
    fun findByUserId(userId: Long, from: LocalDateTime, to: LocalDateTime): List<Event> =
        eventRepository.findByDuration(userId, from, to)

    fun update(userId: Long, eventId: Long, duration: Duration, name: String): Event {
        val event = validateAndGetEvent(userId, eventId)
        event.update(duration = duration, name = name)
        return event
            .also { logger.info { "이벤트 수정: ${it.id}" } }
    }

    fun updateCalendar(userId: Long, eventId: Long, calendarId: Long): Event {
        val event = validateAndGetEvent(userId, eventId)
        val calendar = calendarRepository.find(calendarId)
            ?: throw CalendarNotExistException(key = "id", value = calendarId.toString(), message = "캘린더가 존재하지 않습니다.")
        validate(event.owner, calendar)
        event.calendar = calendar
        return event
            .also { logger.info { "이벤트 캘린더 변경: ${it.id}" } }
    }

    fun updateType(userId: Long, eventId: Long, eventType: EventType): Event {
        val event = validateAndGetEvent(userId, eventId)
        event.type = eventType
        return event
            .also { logger.info { "이벤트 타입 변경: ${it.id}" } }
    }

    fun delete(userId: Long, eventId: Long) {
        val event = validateAndGetEvent(userId, eventId)
            .also { logger.info { "이벤트 삭제: ${it.id}" } }
        eventRepository.delete(event)
    }

    private fun validate(user: User, calendar: Calendar) {
        if (calendar.owner != user)
            throw CalendarForbiddenException(
                userId = user.id,
                key = "calendarId",
                value = calendar.id.toString()
            )
    }

    private fun validateAndGetEvent(userId: Long, eventId: Long): Event {
        val user = userRepository.find(userId)
            ?: throw UserNotExistException(key = "id", value = userId.toString(), message = "회원이 존재하지 않습니다.")
        val event = eventRepository.find(eventId)
            ?: throw EventNotExistException(key = "id", value = eventId.toString(), message = "이벤트가 존재하지 않습니다.")
        validate(user = user, event = event)
        return event
    }

    private fun validate(user: User, event: Event) {
        if (user != event.owner)
            throw EventForbiddenException(
                userId = user.id,
                key = "eventId",
                value = event.id.toString()
            )
    }
}
