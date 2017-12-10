package com.rubengees.teamcitybuildmonitor.domain.api

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author Ruben Gees
 */
data class ApiResponse(
        @JsonProperty("projectStates") val projectStates: List<ProjectState>
)
