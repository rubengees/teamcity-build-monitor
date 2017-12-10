package com.rubengees.teamcitybuildmonitor.domain.teamcity

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author Ruben Gees
 */
data class Build(
        @JsonProperty("status") val status: BuildStatus,
        @JsonProperty("state") val state: BuildState
)