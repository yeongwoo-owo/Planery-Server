package com.planery.api

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.planery.domain.calendar.Calendar
import com.planery.domain.event.Duration
import com.planery.domain.event.Event
import com.planery.domain.event.EventType
import com.planery.domain.event.Schedule
import com.planery.domain.event.Todo

data class EventCreateRequest(
    val calendarId: Long,
    val name: String,
    val duration: Duration,
    private val type: String
) {
    val eventType: EventType
        get() {
            return when (type) {
                "SCHEDULE" -> Schedule()
                "TODO" -> Todo()
                else -> throw NoMatchEventTypeException()
            }
        }
}

class NoMatchEventTypeException : RuntimeException()

class EventResponse(
    val id: Long,
    val calendar: CalendarInfoResponse,
    val name: String,
    val duration: Duration,
    val type: EventTypeResponse
) {
    constructor(event: Event) : this(
        id = event.id,
        calendar = CalendarInfoResponse(event.calendar),
        name = event.name,
        duration = event.duration,
        type = EventTypeResponse(event.type)
    )

    data class CalendarInfoResponse(val id: Long, val name: String, val colorCode: String) {
        constructor(calendar: Calendar) : this(id = calendar.id, name = calendar.name, colorCode = calendar.colorCode)
    }

    data class EventTypeResponse(
        @param:JsonInclude(JsonInclude.Include.NON_NULL)
        val schedule: Schedule?,
        @param:JsonInclude(JsonInclude.Include.NON_NULL)
        val todo: Todo?
    ) {
        constructor(eventType: EventType) : this(
            schedule = eventType as? Schedule,
            todo = eventType as? Todo
        )
    }
}

data class EventListResponse(private val _events: List<Event>) {
    val events = _events.map { EventResponse(event = it) }
}

data class EventUpdateRequest(val duration: Duration, val name: String)
data class EventCalendarUpdateRequest(val calendarId: Long)
data class EventTypeUpdateRequest(@param:JsonProperty("type") private val type: String) {
    val eventType: EventType
        get() {
            return when (type) {
                "SCHEDULE" -> Schedule()
                "TODO" -> Todo()
                else -> throw NoMatchEventTypeException()
            }
        }
}
