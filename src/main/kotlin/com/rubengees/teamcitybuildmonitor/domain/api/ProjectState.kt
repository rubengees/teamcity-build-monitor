package com.rubengees.teamcitybuildmonitor.domain.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.rubengees.teamcitybuildmonitor.domain.teamcity.BuildStatus

/**
 * @author Ruben Gees
 */
data class ProjectState(
        @JsonProperty("name") val name: String,
        @JsonProperty("status") val status: BuildStatus
)
