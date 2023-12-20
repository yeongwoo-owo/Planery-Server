package com.planery.application

abstract class NotExistException(
    entity: String,
    key: String,
    value: String,
    message: String? = null
) : NoSuchElementException(message) {
    val content = NotExistContent(entity, key, value, message)

    data class NotExistContent(
        val entity: String,
        val key: String,
        val value: String,
        val message: String?
    )
}

class UserNotExistException(
    key: String,
    value: String,
    message: String? = null
) : NotExistException("User", key, value, message)

class CalendarNotExistException(
    key: String,
    value: String,
    message: String? = null
) : NotExistException("Calendar", key, value, message)

class EventNotExistException(
    key: String,
    value: String,
    message: String? = null
) : NotExistException("Event", key, value, message)