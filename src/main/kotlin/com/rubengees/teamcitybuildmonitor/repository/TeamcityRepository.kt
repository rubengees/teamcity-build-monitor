package com.rubengees.teamcitybuildmonitor.repository

import org.jetbrains.teamcity.rest.Build
import org.jetbrains.teamcity.rest.BuildConfiguration
import org.jetbrains.teamcity.rest.Project
import org.jetbrains.teamcity.rest.TeamCityInstance
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @author Ruben Gees
 */
@Repository
class TeamcityRepository(@Qualifier("teamcityClient") val client: TeamCityInstance) {

    @Cacheable("teamcity")
    fun getProjects() = getLeafProjects(client.rootProject())

    @Cacheable("teamcity", key = "#project.id")
    fun getBuildConfigurations(project: Project): Flux<BuildConfiguration> = Flux
            .fromIterable(project.fetchBuildConfigurations())

    @Cacheable("teamcity", key = "#buildConfiguration.id")
    fun getLastBuild(buildConfiguration: BuildConfiguration): Mono<Build?> = Mono.justOrEmpty(client.builds()
            .fromConfiguration(buildConfiguration.id)
            .withAnyStatus()
            .latest())

    private fun getLeafProjects(project: Project): Flux<Project> {
        val children = project.fetchChildProjects()

        return when (children.isEmpty()) {
            true -> Flux.fromArray(arrayOf(project))
            false -> Flux.fromIterable(children).flatMap { getLeafProjects(it) }
        }
    }
}
