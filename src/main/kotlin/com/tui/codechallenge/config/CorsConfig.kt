package com.tui.codechallenge.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter

@Configuration
class CorsConfig {
    @Bean
    fun corsWebFilter(): CorsWebFilter {
        val corsConfig = CorsConfiguration()
        corsConfig.addAllowedOrigin("*") // Allow requests from all origins
        corsConfig.addAllowedMethod("*") // Allow all HTTP methods
        corsConfig.addAllowedHeader("*") // Allow all headers

        return CorsWebFilter { corsConfig }
    }
}