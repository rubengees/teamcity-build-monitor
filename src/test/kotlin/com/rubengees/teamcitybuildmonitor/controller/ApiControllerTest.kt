package com.rubengees.teamcitybuildmonitor.controller

import com.rubengees.teamcitybuildmonitor.repository.TeamcityRepository
import org.amshove.kluent.When
import org.amshove.kluent.any
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.amshove.kluent.mock
import org.amshove.kluent.shouldBeEmpty
import org.jetbrains.teamcity.rest.Build
import org.junit.Before
import org.junit.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @author Ruben Gees
 */
class ApiControllerTest {

    lateinit var repository: TeamcityRepository
    lateinit var subject: ApiController

    @Before
    fun setUp() {
        repository = mock(TeamcityRepository::class)
        subject = ApiController(repository)

        When calling repository.getProjects() itReturns Flux.empty()
        When calling repository.getBuildConfigurations(any()) itReturns Flux.empty()
        When calling repository.getLastBuild(any()) itReturns Mono.just(mock(Build::class))
    }

    @Test
    fun getTeamcityStatus() {
        val result = subject.teamcityStatus().block()

        result!!.projectStates.shouldBeEmpty()
    }
}
