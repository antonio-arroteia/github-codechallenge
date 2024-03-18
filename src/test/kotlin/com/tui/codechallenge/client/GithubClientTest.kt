package com.tui.codechallenge.client

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
class GithubClientTest {

    private val mockWebClient = mockk<WebClient>()

    private val githubClient = GithubClient(mockWebClient)

    @Test
    fun `getUserRepositories should return repositories`() {
        val username = "testuser"
        val expectedRepositories = listOf(
            GithubRepository("repo1", Owner("owner2"), false),
            GithubRepository("repo2", Owner("owner2"), false))

        every {
            mockWebClient.get()
                .uri("/users/{username}/repos?type=owner", username)
                .retrieve()
                .bodyToFlux(GithubRepository::class.java)
        } returns Flux.fromIterable(expectedRepositories)

        val result = githubClient.getUserRepositories(username)

        StepVerifier.create(result)
            .expectNextSequence(expectedRepositories)
            .verifyComplete()
    }

    @Test
    fun `getUserBranches should return branches`() {
        val username = "testuser"
        val repository = "testrepo"
        val expectedBranches = listOf(GithubBranch("branch1", CommitInfo("sha1", "url1"), false), GithubBranch("branch2", CommitInfo("sha1", "url1"), false))

        every {
            mockWebClient.get()
                .uri("/repos/{owner}/{repo}/branches", username, repository)
                .retrieve()
                .bodyToFlux(GithubBranch::class.java)
        } returns Flux.fromIterable(expectedBranches)

        val result = githubClient.getUserBranches(username, repository)

        StepVerifier.create(result)
            .expectNextSequence(expectedBranches)
            .verifyComplete()
    }
}


