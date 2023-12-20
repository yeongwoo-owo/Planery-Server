package com.planery.domain.calendar

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun CalendarRepository.find(calendarId: Long): Calendar? = findByIdOrNull(calendarId)
fun CalendarRepository.findAllByUserId(userId: Long): List<Calendar> = findAllByOwnerId(userId)

interface CalendarRepository : JpaRepository<Calendar, Long> {
    fun findAllByOwnerId(userId: Long): List<Calendar>
}
