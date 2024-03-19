package com.tui.codechallenge.client

import com.tui.codechallenge.model.GithubBranch
import com.tui.codechallenge.model.GithubRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@Component
class GithubClient(private val webClient: WebClient) {

    private val logger = LoggerFactory.getLogger(GithubClient::class.java)

    fun getUserRepositories(username: String): Flux<GithubRepository> {
        logger.info("Calling Github REST API on retrieving repositories")
        return webClient.get()
            .uri("/users/{username}/repos?type=owner", username)
            .retrieve()
            .bodyToFlux(GithubRepository::class.java)
    }

    fun getUserBranches(username: String, repository: String): Flux<GithubBranch> {
        logger.info("Calling Github REST API on retrieving branches for repository: $repository")
        return webClient.get()
            .uri("/repos/{owner}/{repo}/branches", username, repository)
            .retrieve()
            .bodyToFlux(GithubBranch::class.java)
    }

}