package com.tui.codechallenge.service

import com.tui.codechallenge.client.GithubClient
import com.tui.codechallenge.model.CommitInfo
import com.tui.codechallenge.model.GithubBranch
import com.tui.codechallenge.model.GithubRepository
import com.tui.codechallenge.model.Owner
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

@ExtendWith(SpringExtension::class)
class GithubServiceTest {

    private val mockWebClient = mockk<WebClient>()

    private val githubClient = GithubClient(mockWebClient)

    private val githubService = GithubService(githubClient)

    @Test
    fun testGetUserRepositories() {

        val githubBranch = GithubBranch("master", CommitInfo("sha", "test"), false)
        val githubRepository = GithubRepository("repo1", Owner("user1"), false)
        every {
            mockWebClient.get()
                .uri("/users/{username}/repos?type=owner", "user1")
                .retrieve()
                .bodyToFlux(GithubRepository::class.java)
        } returns Flux.just(githubRepository)

        every {
            mockWebClient.get()
                .uri("/repos/{owner}/{repo}/branches", "user1", "repo1")
                .retrieve()
                .bodyToFlux(GithubBranch::class.java)
        } returns Flux.just(githubBranch)


        val result = githubService.getUserRepositories("user1")
        StepVerifier.create(result)
            .expectNextMatches { it.repositoryName == "repo1" }
            .verifyComplete()
    }
}