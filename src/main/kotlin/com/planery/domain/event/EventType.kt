package com.planery.domain.event

import com.planery.support.domain.BaseRootEntity
import jakarta.persistence.Entity
import jakarta.persistence.Inheritance
import java.time.LocalDateTime

@Entity
@Inheritance
abstract class EventType : BaseRootEntity()

@Entity
class Schedule : EventType()

@Entity
data class Todo(private var doneAt: LocalDateTime? = null) : EventType() {
    val isDone: Boolean get() = doneAt != null
}