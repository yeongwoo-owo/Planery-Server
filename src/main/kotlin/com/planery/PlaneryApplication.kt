package com.planery

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PlaneryApplication

fun main(args: Array<String>) {
    runApplication<PlaneryApplication>(*args)
}
