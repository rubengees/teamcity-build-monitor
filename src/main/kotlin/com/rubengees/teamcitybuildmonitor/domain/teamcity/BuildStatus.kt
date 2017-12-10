package com.rubengees.teamcitybuildmonitor.domain.teamcity

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author Ruben Gees
 */
enum class BuildStatus {
    @JsonProperty("SUCCESS")
    SUCCESS,
    @JsonProperty("FAILURE")
    FAILURE,
    @JsonProperty("NONE")
    NONE
}
