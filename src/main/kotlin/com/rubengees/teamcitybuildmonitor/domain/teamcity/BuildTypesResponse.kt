package com.rubengees.teamcitybuildmonitor.domain.teamcity

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author Ruben Gees
 */
data class BuildTypesResponse(
        @JsonProperty("count") val count: Int,
        @JsonProperty("buildType") val buildTypes: List<BuildType>
)
