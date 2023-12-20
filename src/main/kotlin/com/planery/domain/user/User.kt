package com.planery.domain.user

import com.planery.support.domain.BaseRootEntity
import com.planery.support.domain.SoftDeleteEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDateTime

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at is null")
class User(
    var name: String,
    override var deletedAt: LocalDateTime? = null,
    id: Long = 0L
) : BaseRootEntity(id), SoftDeleteEntity
