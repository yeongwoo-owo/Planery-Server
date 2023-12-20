package com.planery

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class PlaneryApplication

fun main(args: Array<String>) {
    runApplication<PlaneryApplication>(*args)
}
