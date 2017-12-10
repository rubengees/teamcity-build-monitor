package com.rubengees.teamcitybuildmonitor.domain.teamcity

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author Ruben Gees
 */
data class BuildType(
        @JsonProperty("id") val id: String,
        @JsonProperty("name") val name: String,
        @JsonProperty("projectName") val projectName: String,
        @JsonProperty("projectId") val projectId: String
)
