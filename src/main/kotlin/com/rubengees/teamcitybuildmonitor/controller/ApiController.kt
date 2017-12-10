package com.rubengees.teamcitybuildmonitor.controller

import com.rubengees.teamcitybuildmonitor.ConfigProperties
import com.rubengees.teamcitybuildmonitor.domain.api.ApiResponse
import com.rubengees.teamcitybuildmonitor.domain.api.ProjectState
import com.rubengees.teamcitybuildmonitor.domain.teamcity.Build
import com.rubengees.teamcitybuildmonitor.domain.teamcity.BuildStatus
import com.rubengees.teamcitybuildmonitor.repository.TeamcityRepository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Ruben Gees
 */
@RestController
class ApiController(private val repository: TeamcityRepository) {

    @RequestMapping("/teamcityStatus")
    fun getTeamcityStatus() = ApiResponse(repository.getBuildTypes().map {
        ProjectState(it.name, repository.getLastBuildStatus(it.id)?.status ?: BuildStatus.NONE)
    })
}
