package com.planery.domain.event

import jakarta.persistence.Embeddable
import java.time.LocalDate
import java.time.LocalDateTime

@Embeddable
data class Duration(val start: LocalDateTime, val end: LocalDateTime) {
    constructor(start: LocalDate, end: LocalDate) : this(start.atStartOfDay(), end.atStartOfDay())
}