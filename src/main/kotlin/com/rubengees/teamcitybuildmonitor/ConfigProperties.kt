package com.rubengees.teamcitybuildmonitor

import org.hibernate.validator.constraints.NotBlank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Max
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

    @NotBlank
    lateinit var username: String

    @NotBlank
    lateinit var password: String

    @Min(5000)
    var interval = 0L
}
