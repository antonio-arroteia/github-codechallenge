package com.tui.codechallenge.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    private val githubUserToken = "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"
    @Bean
    fun webClient(): WebClient {
        return WebClient.builder()
            .baseUrl("https://api.github.com")
            .defaultHeader("Authorization", "Bearer $githubUserToken")
            .build()
    }
}