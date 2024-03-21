package com.tui.codechallenge.controller

import com.ninjasquad.springmockk.MockkBean
import com.tui.codechallenge.client.GithubClient
import com.tui.codechallenge.controller.RestApiElements.URL_API_BASE
import com.tui.codechallenge.exception.ErrorResponse
import com.tui.codechallenge.model.GithubRepoResponse
import com.tui.codechallenge.model.GithubRepository
import com.tui.codechallenge.model.Owner
import io.mockk.every
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.client.WebClientResponseException.NotFound
import reactor.core.publisher.Flux
import kotlin.test.assertEquals
import kotlin.test.assertTrue


@SpringBootTest
@AutoConfigureWebTestClient
@ExtendWith(SpringExtension::class)
class GithubControllerIntegrationTest {

    @Autowired
    private lateinit var webClient: WebTestClient

    @MockkBean
    private lateinit var githubClient: GithubClient


    @Test
    fun `getUserRepositories should return repositories`() {
        val username = "testuser"
        val clientData = mutableListOf(
            GithubRepository("repo1", Owner("owner2"), false),
            GithubRepository("repo2", Owner("owner2"), false)
        )

        val expectedResponse =  mutableListOf(
            GithubRepoResponse("repo1", "owner2", emptyList()),
            GithubRepoResponse("repo2", "owner2", emptyList())
        )

        every { githubClient.getUserRepositories("testuser") } returns Flux.fromIterable(clientData)
        every { githubClient.getUserBranches(any(), any()) } returns Flux.empty()


        val result = webClient.post()
            .uri(URL_GET_USER_REPOS)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(username)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(GithubRepoResponse::class.java)
            .returnResult()

        assertEquals(expectedResponse, result.responseBody)
    }

    @Test
    fun `test getUserRepositories with non-existing username`() {
        val username = "nonExistingUser"

        every { githubClient.getUserRepositories(username) }.throws(NotFound.create(404,"Not Found", HttpHeaders.EMPTY, byteArrayOf(), null))

        val result = webClient.post().uri(URL_GET_USER_REPOS)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(username)
            .exchange()
            .expectStatus().isNotFound
            .expectBody(ErrorResponse::class.java)
            .returnResult()

        assertTrue(result.status.isSameCodeAs(HttpStatusCode.valueOf(404)))
        assertEquals("Not Found", result.responseBody!!.status)
    }


    @Test
    fun `test getUserRepositories with invalid accept header`() {
        val username = "testUser"

        val result = webClient.post().uri(URL_GET_USER_REPOS)
            .header(HttpHeaders.ACCEPT, "wrong header")
            .bodyValue(username)
            .exchange()
            .expectBody(ErrorResponse::class.java)
            .returnResult()

        assertTrue(result.status.isSameCodeAs(HttpStatusCode.valueOf(406)))
    }


    companion object{
        const val URL_GET_USER_REPOS = "$URL_API_BASE/repositories"
    }
}