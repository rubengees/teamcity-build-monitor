package com.rubengees.teamcitybuildmonitor

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class TeamcityBuildMonitorApplication

fun main(args: Array<String>) {
    SpringApplication.run(TeamcityBuildMonitorApplication::class.java, *args)
}
