package com.planery.api

import com.planery.domain.user.User

data class CookieResponse(val cookie: String) {
    constructor(name: String, value: String): this("$name=$value")
}

data class UserResponse(val userId: Long, val name: String) {
    constructor(user: User): this(userId = user.id, name = user.name)
}