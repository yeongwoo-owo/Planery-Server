package com.planery.application

abstract class ForbiddenException(
    userId: Long,
    entity: String,
    key: String,
    value: String,
    message: String? = "접근 권한이 없습니다.",
) : RuntimeException(message) {
    val content = ForbiddenContent(userId = userId, entity = entity, key = key, value = value, message = message)

    data class ForbiddenContent(
        val userId: Long,
        val entity: String,
        val key: String,
        val value: String,
        val message: String?
    )
}

class CalendarForbiddenException(
    userId: Long,
    key: String,
    value: String,
    message: String? = "캘린더 접근 권한이 없습니다."
) : ForbiddenException(userId = userId, entity = "Calendar", key = key, value = value, message = message)

class EventForbiddenException(
    userId: Long,
    key: String,
    value: String,
    message: String? = "이벤트 접근 권한이 없습니다."
) : ForbiddenException(userId = userId, entity = "Event", key = key, value = value, message = message)