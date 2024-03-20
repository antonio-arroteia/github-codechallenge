package com.tui.codechallenge.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(
    @Value("\${git.hub.access-token}")
    private val githubUserToken: String
) {
    @Bean
    fun webClient(): WebClient {
        return WebClient.builder()
            .baseUrl("https://api.github.com")
            .defaultHeader("Authorization", "Bearer $githubUserToken") //needed for when the access to the repo is private
            .build()
    }
}