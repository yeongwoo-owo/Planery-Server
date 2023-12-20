package com.planery.domain.event

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

fun EventRepository.find(eventId: Long) = findByIdOrNull(eventId)
fun EventRepository.findByUserId(userId: Long) = findByOwnerId(userId)

interface EventRepository : JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e JOIN FETCH e.calendar JOIN FETCH e.type " +
            "WHERE e.owner.id = :userId " +
            "ORDER BY e.duration.start, e.duration.end")
    fun findByOwnerId(userId: Long): List<Event>

    @Query("SELECT e FROM Event e JOIN FETCH e.calendar JOIN FETCH e.type " +
            "WHERE e.owner.id = :userId " +
            "AND NOT(e.duration.end < :from OR e.duration.start >= :to) " +
            "ORDER BY e.duration.start, e.duration.end")
    fun findByDuration(userId: Long, from: LocalDateTime, to: LocalDateTime): List<Event>

    @Query("SELECT e FROM Event e JOIN FETCH e.calendar JOIN FETCH e.type " +
            "WHERE e.id = :eventId")
    fun findByIdOrNull(eventId: Long): Event?
}
