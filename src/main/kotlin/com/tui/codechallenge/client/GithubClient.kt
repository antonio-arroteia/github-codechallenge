package com.tui.codechallenge.client

import com.tui.codechallenge.model.GithubBranch
import com.tui.codechallenge.model.GithubRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@Component
class GithubClient(private val webClient: WebClient) {

    fun getUserRepositories(username: String): Flux<GithubRepository> {
        return webClient.get()
            .uri("/users/{username}/repos?type=owner", username)
            .retrieve()
            .bodyToFlux(GithubRepository::class.java)
    }

    fun getUserBranches(username: String, repository: String): Flux<GithubBranch> {
        return webClient.get()
            .uri("/repos/{owner}/{repo}/branches", username, repository)
            .retrieve()
            .bodyToFlux(GithubBranch::class.java)
    }

}