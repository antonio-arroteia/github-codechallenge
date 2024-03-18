package com.tui.codechallenge.controller

import com.ninjasquad.springmockk.MockkBean
import com.tui.codechallenge.client.GithubClient
import com.tui.codechallenge.model.Branch
import com.tui.codechallenge.model.CommitInfo
import com.tui.codechallenge.model.GithubBranch
import com.tui.codechallenge.model.GithubRepoResponse
import com.tui.codechallenge.model.GithubRepository
import com.tui.codechallenge.model.Owner
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import kotlin.test.Ignore


@SpringBootTest
@AutoConfigureWebTestClient
class GithubControllerIntegrationTest {

    @Autowired
    private lateinit var webClient: WebTestClient

    @MockkBean
    private lateinit var githubClient: GithubClient

    @Test
    @Ignore
    fun `getUserRepositories should return repositories`() {
        val username = "testuser"
        val responseList = listOf(
            GithubRepository("repo1", Owner("owner2"), false),
            GithubRepository("repo2", Owner("owner2"), false)
        )

        every {
            githubClient.getUserRepositories(username)
        } returns Flux.fromIterable(responseList)

        webClient.get()
            .uri("/api/repositories/$username")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(GithubRepoResponse::class.java)
            .hasSize(2) // Assuming two repositories are returned
    }
}