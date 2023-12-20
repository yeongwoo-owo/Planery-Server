package com.planery.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun UserRepository.find(id: Long): User? = findByIdOrNull(id)

interface UserRepository : JpaRepository<User, Long>
