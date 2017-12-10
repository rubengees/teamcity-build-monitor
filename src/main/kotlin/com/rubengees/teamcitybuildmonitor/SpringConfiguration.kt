package com.rubengees.teamcitybuildmonitor

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.web.client.RestTemplate

/**
 * @author Ruben Gees
 */
@Configuration
class SpringConfiguration(val config: ConfigProperties) {

    @Bean(name = ["teamcityClient"])
    fun getTeamcityClient(): RestTemplate = RestTemplateBuilder()
            .basicAuthorization(config.username, config.password)
            .interceptors(ClientHttpRequestInterceptor { request, body, execution ->
                request.headers.accept = listOf(MediaType.APPLICATION_JSON)

                execution.execute(request, body)
            })
            .build()
}