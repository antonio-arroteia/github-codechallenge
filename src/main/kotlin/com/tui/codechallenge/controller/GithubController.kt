package com.tui.codechallenge.controller

import com.tui.codechallenge.controller.RestApiElements.URL_API_BASE
import com.tui.codechallenge.model.GithubRepoResponse
import com.tui.codechallenge.service.GithubService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClientResponseException.NotAcceptable
import reactor.core.publisher.Flux

@RestController
@RequestMapping(URL_API_BASE)
class GithubController(private val githubService: GithubService) {

    @GetMapping(URL_GET_USER_REPOSITORIES, produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(description = "Get user's Repositories which are not forks and correspondent branches")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Success"),
            ApiResponse(responseCode = "404", description = "Not Found"),
            ApiResponse(responseCode = "406", description = "Not Acceptable"),
        ]
    )
    fun getUserRepositories(@Parameter(name = "username", description =  "Github's username/owner name") @PathVariable username: String,
                            @RequestHeader(HttpHeaders.ACCEPT) acceptHeader: String?
    ):Flux<GithubRepoResponse> {
        if(acceptHeader != null && acceptHeader != URL_HEADER_ACCEPT_VALUE ){
            throw NotAcceptable.create(406,"Only application/json is supported", HttpHeaders.EMPTY, byteArrayOf(), null)
        }
        return githubService.getUserRepositories(username)
    }


    companion object{
        const val URL_GET_USER_REPOSITORIES = "/repositories/{username}"

        const val URL_HEADER_ACCEPT_VALUE = "application/json"
    }
}

