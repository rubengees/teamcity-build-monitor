package com.rubengees.teamcitybuildmonitor.repository

import org.jetbrains.teamcity.rest.BuildConfiguration
import org.jetbrains.teamcity.rest.Project
import org.jetbrains.teamcity.rest.TeamCityInstance
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository

/**
 * @author Ruben Gees
 */
@Repository
class TeamcityRepository(@Qualifier("teamcityClient") val client: TeamCityInstance) {

    @Cacheable("teamcity")
    fun getProjects() = getLeafProjects(client.rootProject())

    @Cacheable("teamcity", key = "#project.id")
    fun getBuildConfigurations(project: Project) = project.fetchBuildConfigurations()

    @Cacheable("teamcity", key = "#buildConfiguration.id")
    fun getLastBuild(buildConfiguration: BuildConfiguration) = client.builds()
            .fromConfiguration(buildConfiguration.id)
            .withAnyStatus()
            .latest()

    private fun getLeafProjects(project: Project): List<Project> {
        val children = project.fetchChildProjects()

        return when (children.isEmpty()) {
            true -> listOf(project)
            false -> children.flatMap { getLeafProjects(it) }
        }
    }
}
