package com.rubengees.teamcitybuildmonitor

import org.hibernate.validator.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated
import javax.annotation.PostConstruct
import javax.validation.constraints.Min

/**
 * @author Ruben Gees
 */
@Validated
@Configuration
@ConfigurationProperties(prefix = "teamcity")
class ConfigProperties {

    @NotBlank
    lateinit var url: String

    var username: String? = null

    var password: String? = null

    @Min(5000)
    var interval = 0L

    @PostConstruct
    private fun init() {
        url = url.trimEnd { it == '/' }
    }
}
