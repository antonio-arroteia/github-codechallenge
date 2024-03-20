package com.tui.codechallenge.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {
    @org.springframework.web.bind.annotation.ExceptionHandler(WebClientResponseException.NotFound::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException(ex: WebClientResponseException.NotFound): ResponseEntity<ErrorResponse> {
        logger.error("No user found")
        return ResponseEntity.status(ex.statusCode).body(ErrorResponse(ex.statusText, ex.message))
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(WebClientResponseException.NotAcceptable::class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    fun handleNotAcceptableException(ex: WebClientResponseException.NotAcceptable): ResponseEntity<ErrorResponse> {
        logger.error("Request had an incorrect header, must be \"application/json\"")
        return ResponseEntity.status(ex.statusCode).body(ErrorResponse("Not Acceptable", ex.statusText))
    }

}

data class ErrorResponse(
    val status: String,
    val message: String?
)



