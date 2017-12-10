package com.rubengees.teamcitybuildmonitor.repository

import com.rubengees.teamcitybuildmonitor.ConfigProperties
import com.rubengees.teamcitybuildmonitor.domain.teamcity.BuildState
import com.rubengees.teamcitybuildmonitor.domain.teamcity.BuildType
import com.rubengees.teamcitybuildmonitor.domain.teamcity.BuildTypesResponse
import com.rubengees.teamcitybuildmonitor.domain.teamcity.BuildsResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate

/**
 * @author Ruben Gees
 */
@Repository
class TeamcityRepository(
        @Qualifier("teamcityClient") val client: RestTemplate,
        val config: ConfigProperties
) {

    fun getBuildTypes(): List<BuildType> = client
            .getForEntity("${config.url}/app/rest/buildTypes", BuildTypesResponse::class.java)
            .body.buildTypes

    fun getLastBuildStatus(buildType: String) = client
            .getForEntity("${config.url}/app/rest/builds?locator=buildType:$buildType", BuildsResponse::class.java)
            .body.builds.firstOrNull { it.state == BuildState.FINISHED }
}
