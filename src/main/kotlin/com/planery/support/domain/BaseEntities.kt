package com.planery.support.domain

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseRootEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)

@MappedSuperclass
abstract class BaseAuditEntity(
    id: Long = 0L,
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    val updatedAt: LocalDateTime = createdAt
) : BaseRootEntity(id)

interface SoftDeleteEntity {
    var deletedAt: LocalDateTime?
    val isDeleted: Boolean
        get() = deletedAt == null
}
