package com.rubengees.teamcitybuildmonitor

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.Ticker
import org.jetbrains.teamcity.rest.TeamCityInstanceFactory
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

    @Bean(name = ["teamcityClient"])
    fun teamcityClient() = TeamCityInstanceFactory.httpAuth(config.url, config.username, config.password)

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
