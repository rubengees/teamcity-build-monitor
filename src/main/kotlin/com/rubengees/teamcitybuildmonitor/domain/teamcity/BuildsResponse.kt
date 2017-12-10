package com.rubengees.teamcitybuildmonitor.domain.teamcity

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author Ruben Gees
 */
data class BuildsResponse(@JsonProperty("build") val builds: List<Build>)
