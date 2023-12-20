package com.planery.support.dev

import com.planery.domain.calendar.Calendar
import com.planery.domain.calendar.CalendarRepository
import com.planery.domain.event.Duration
import com.planery.domain.event.Event
import com.planery.domain.event.EventRepository
import com.planery.domain.event.Schedule
import com.planery.domain.event.Todo
import com.planery.domain.user.User
import com.planery.domain.user.UserRepository
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

@Component
class DevDataInitializer(private val dev: DevData) {

    @PostConstruct
    fun init() {
        dev.init()
    }

    @Component
    @Transactional
    class DevData(
        private val userRepository: UserRepository,
        private val calendarRepository: CalendarRepository,
        private val eventRepository: EventRepository
    ) {
        fun init() {
            val user = userRepository.save(User("유저A"))
            if (calendarRepository.findAllByOwnerId(user.id).isNotEmpty()) return
            val calendars = calendarRepository.saveAll(
                listOf(
                    Calendar(user, "캘린더A", "ff0000"),
                    Calendar(user, "캘린더B", "0067a3")
                )
            )
            val start = LocalDate.of(2023, 11, 1)
            val end = LocalDate.of(2024, 2, 1)
            val events = eventRepository.saveAll(
                generateSequence(start) { it.plusDays(5) }
                    .takeWhile { it < end }
                    .mapIndexed { index, date ->
                        Event(
                            owner = user,
                            calendar = calendars.random(),
                            duration = Duration(date, date.plusDays((1L..7L).random())),
                            type = listOf(Schedule(), Todo(), Todo(doneAt = LocalDateTime.now())).random(),
                            name = "이벤트 $index"
                        )
                    }
                    .toList()
            )
        }
    }
}