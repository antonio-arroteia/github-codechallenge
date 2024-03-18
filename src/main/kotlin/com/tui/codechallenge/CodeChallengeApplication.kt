package com.tui.codechallenge

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
@OpenAPIDefinition( info = Info(
	title = "Github get users repositories API", version = "1.0", description = "API Documentation"
)
)
class CodeChallengeApplication

fun main(args: Array<String>) {
	runApplication<CodeChallengeApplication>(*args)
}
