package com.planery.api

import com.planery.application.EventService
import com.planery.domain.event.Event
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/events")
class EventController(private val eventService: EventService) {
    @PostMapping
    fun create(
        @RequestAttribute loginId: Long,
        @RequestBody request: EventCreateRequest
    ): ResponseEntity<EventResponse> {
        val event = eventService.create(
            userId = loginId,
            calendarId = request.calendarId,
            eventType = request.eventType,
            duration = request.duration,
            name = request.name
        )
        return ResponseEntity.ok(EventResponse(event))
    }

    @GetMapping
    fun findByUserId(
        @RequestAttribute loginId: Long,
        @RequestParam(required = false) from: LocalDateTime?,
        @RequestParam(required = false) to: LocalDateTime?
    ): ResponseEntity<EventListResponse> {
        val events: List<Event> = if (from != null && to != null) {
            eventService.findByUserId(loginId, from, to)
        } else {
            eventService.findByUserId(loginId)
        }
        return ResponseEntity.ok(EventListResponse(events))
    }

    @PutMapping("/{eventId}")
    fun update(
        @RequestAttribute loginId: Long,
        @PathVariable eventId: Long,
        @RequestBody request: EventUpdateRequest
    ): ResponseEntity<EventResponse> {
        val event = eventService.update(
            userId = loginId, eventId = eventId, duration = request.duration, name = request.name
        )
        return ResponseEntity.ok(EventResponse(event))
    }

    @PatchMapping("/{eventId}/calendar")
    fun updateCalendar(
        @RequestAttribute loginId: Long,
        @PathVariable eventId: Long,
        @RequestBody request: EventCalendarUpdateRequest
    ): ResponseEntity<EventResponse> {
        val event = eventService.updateCalendar(
            userId = loginId, eventId = eventId, calendarId = request.calendarId
        )
        return ResponseEntity.ok(EventResponse(event))
    }

    @PatchMapping("/{eventId}/type")
    fun updateType(
        @RequestAttribute loginId: Long,
        @PathVariable eventId: Long,
        @RequestBody request: EventTypeUpdateRequest
    ): ResponseEntity<EventResponse> {
        val event = eventService.updateType(
            userId = loginId, eventId = eventId, eventType = request.eventType
        )
        return ResponseEntity.ok(EventResponse(event))
    }

    @PostMapping("/{eventId}/mark")
    fun doEvent(
        @RequestAttribute loginId: Long,
        @PathVariable eventId: Long,
    ): ResponseEntity<EventResponse> {
        val event = eventService.mark(loginId, eventId)
        return ResponseEntity.ok(EventResponse(event))
    }

    @DeleteMapping("/{eventId}/mark")
    fun undoEvent(
        @RequestAttribute loginId: Long,
        @PathVariable eventId: Long,
    ): ResponseEntity<EventResponse> {
        val event = eventService.unmark(loginId, eventId)
        return ResponseEntity.ok(EventResponse(event))
    }

    @DeleteMapping("/{eventId}")
    fun delete(
        @RequestAttribute loginId: Long,
        @PathVariable eventId: Long,
    ): ResponseEntity<Unit> {
        eventService.delete(userId = loginId, eventId = eventId)
        return ResponseEntity.noContent().build()
    }
}
