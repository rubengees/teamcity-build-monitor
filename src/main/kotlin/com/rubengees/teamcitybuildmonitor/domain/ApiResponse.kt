package com.rubengees.teamcitybuildmonitor.domain

import com.fasterxml.jackson.annotation.JsonProperty
import org.jetbrains.teamcity.rest.BuildStatus

/**
 * @author Ruben Gees
 */
data class ApiResponse(@JsonProperty("projectStates") val projectStates: List<ProjectState>) {

    data class ProjectState(
            @JsonProperty("name") val name: String,
            @JsonProperty("status") val status: BuildStatus?,
            @JsonProperty("branchName") val branchName: String?,
            @JsonProperty("buildNumber") val buildNumber: String?,
            @JsonProperty("dateTime") val dateTime: String
    )
}
