package com.rubengees.teamcitybuildmonitor.controller

import com.rubengees.teamcitybuildmonitor.domain.ApiResponse
import com.rubengees.teamcitybuildmonitor.domain.ApiResponse.ProjectState
import com.rubengees.teamcitybuildmonitor.repository.TeamcityRepository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat
import java.util.TimeZone

/**
 * @author Ruben Gees
 */
@RestController
class ApiController(private val repository: TeamcityRepository) {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    @RequestMapping("/teamcityStatus")
    fun getTeamcityStatus() = ApiResponse(repository.getProjects()
            .flatMap { repository.getBuildConfigurations(it) }
            .map { it to repository.getLastBuild(it) }
            .map { (buildConfig, build) ->
                val name = buildConfig.name
                val status = build?.status
                val branchName = build?.branch?.name?.replace("refs/heads/", "")
                val buildNumber = build?.buildNumber
                val dateTime = build?.fetchFinishDate()

                ProjectState(name, status, branchName, buildNumber, dateFormat.format(dateTime))
            }
    )
}
