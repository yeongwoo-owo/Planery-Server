package com.planery.api

import com.planery.domain.calendar.Calendar

data class CalendarRequest(val name: String, val colorCode: String)
data class CalendarResponse(val id: Long, val name: String, val colorCode: String) {
    constructor(calendar: Calendar) : this(calendar.id, calendar.name, calendar.colorCode)
}

data class CalendarListResponse(private val _calendars: List<Calendar>) {
    val calendars: List<CalendarResponse> = _calendars.map { CalendarResponse(it) }
}
