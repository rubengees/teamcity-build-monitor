package com.rubengees.teamcitybuildmonitor

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.Ticker
import org.jetbrains.teamcity.rest.TeamCityInstance
import org.jetbrains.teamcity.rest.TeamCityInstanceFactory
import org.slf4j.LoggerFactory
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

/**
 * @author Ruben Gees
 */
@Configuration
class SpringConfiguration(val config: ConfigProperties) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Bean(name = ["teamcityClient"])
    fun teamcityClient(): TeamCityInstance {
        val safeUsername = config.username
        val safePassword = config.password

        if (safeUsername == null || safeUsername.isBlank() || safePassword == null || safePassword.isBlank()) {
            logger.warn("Username or password is empty. Falling back to guest auth.")

            return TeamCityInstanceFactory.guestAuth(config.url)
        } else {
            return TeamCityInstanceFactory.httpAuth(config.url, safeUsername, safePassword)
        }
    }

    @Bean
    fun cacheManager(ticker: Ticker) = SimpleCacheManager().apply {
        setCaches(listOf(CaffeineCache("teamcity", Caffeine.newBuilder()
                .expireAfterWrite(config.interval, TimeUnit.MILLISECONDS)
                .ticker(ticker)
                .build())))
    }

    @Bean
    fun ticker() = Ticker.systemTicker()
}
