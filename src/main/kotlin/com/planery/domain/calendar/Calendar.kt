package com.planery.domain.calendar

import com.planery.domain.user.User
import com.planery.support.domain.BaseAuditEntity
import com.planery.support.domain.SoftDeleteEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDateTime

@Entity
@SQLDelete(sql = "UPDATE calendar SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at is null")
class Calendar(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val owner: User,
    var name: String,
    var colorCode: String,
    override var deletedAt: LocalDateTime? = null,
    id: Long = 0L
) : BaseAuditEntity(id), SoftDeleteEntity {
    fun update(name: String, colorCode: String) {
        this.name = name
        this.colorCode = colorCode
    }
}
