package com.rubengees.teamcitybuildmonitor.repository

import com.nhaarman.mockito_kotlin.any
import com.rubengees.teamcitybuildmonitor.ConfigProperties
import com.rubengees.teamcitybuildmonitor.domain.teamcity.BuildType
import com.rubengees.teamcitybuildmonitor.domain.teamcity.BuildTypesResponse
import org.amshove.kluent.When
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.amshove.kluent.mock
import org.amshove.kluent.shouldEqual
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

/**
 * @author Ruben Gees
 */
class TeamcityRepositoryTest {

    lateinit var client: RestTemplate
    lateinit var config: ConfigProperties
    lateinit var repository: TeamcityRepository

    @Before
    fun setUp() {
        client = mock(RestTemplate::class)
        config = ConfigProperties().apply {
            url = "mock"
            username = "mock"
            password = "mock"
        }

        repository = TeamcityRepository(client, config)

        When calling client.getForEntity(any<String>(), any<Class<*>>()) itReturns
                ResponseEntity.ok(BuildTypesResponse(1, listOf(BuildType("1", "name", "project", "2"))))
    }

    @Test
    fun testGetBuildTypes() {
        repository.getBuildTypes() shouldEqual listOf(BuildType("1", "name", "project", "2"))
    }
}