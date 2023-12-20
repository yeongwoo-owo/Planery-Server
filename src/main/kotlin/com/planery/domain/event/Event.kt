package com.planery.domain.event

import com.planery.domain.calendar.Calendar
import com.planery.domain.user.User
import com.planery.support.domain.BaseAuditEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "events")
class Event(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    val owner: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id")
    var calendar: Calendar,

    @Embedded
    var duration: Duration,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "event_type_id")
    var type: EventType,

    var name: String,
    id: Long = 0L
) : BaseAuditEntity(id) {
    fun update(duration: Duration, name: String) {
        this.duration = duration
        this.name = name
    }
}
