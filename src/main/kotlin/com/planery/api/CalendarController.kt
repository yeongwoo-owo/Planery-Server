package com.planery.api

import com.planery.application.CalendarService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/calendars")
class CalendarController(
    private val calendarService: CalendarService
) {
    @PostMapping
    fun create(
        @RequestAttribute loginId: Long,
        @RequestBody request: CalendarRequest
    ): ResponseEntity<CalendarResponse> {
        val calendar = calendarService.create(
            userId = loginId, name = request.name, colorCode = request.colorCode
        )
        return ResponseEntity.ok(CalendarResponse(calendar))
    }

    @GetMapping
    fun findByUserId(@RequestAttribute loginId: Long): ResponseEntity<CalendarListResponse> {
        val calendars = calendarService.findByUserId(loginId)
        return ResponseEntity.ok(CalendarListResponse(calendars))
    }

    @PutMapping("/{calendarId}")
    fun update(
        @RequestAttribute loginId: Long,
        @PathVariable calendarId: Long,
        @RequestBody request: CalendarRequest
    ): ResponseEntity<CalendarResponse> {
        val calendar = calendarService.update(
            userId = loginId, calendarId = calendarId, name = request.name, colorCode = request.colorCode
        )
        return ResponseEntity.ok(CalendarResponse(calendar))
    }

    @DeleteMapping("/{calendarId}")
    fun delete(
        @RequestAttribute loginId: Long,
        @PathVariable calendarId: Long,
    ): ResponseEntity<Unit> {
        calendarService.delete(userId = loginId, calendarId = calendarId)
        return ResponseEntity.noContent().build()
    }
}
