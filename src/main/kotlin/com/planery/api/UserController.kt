package com.planery.api

import com.planery.application.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping("/me")
    fun findMe(@RequestAttribute loginId: Long): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(UserResponse(userService.find(loginId)))
    }
}