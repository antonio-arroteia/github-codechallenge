## Table of Contents

- [Introduction](#introduction)
- [Pre Requisites](#prerequisites)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [API Documentation](#documentation)
- [Future Implementations](#future)
- [Personal Considerations](#considerations)


## Introduction

This API implements the functionality of getting all the GitHub repositories that are not forks and correspondent 
branch information in a single endpoint. 


## Pre Requisites

- Java 21
- Docker 20.10.14 (Optional)


## Getting Started

Clone the project to your local environment and build it using gradle command at the root project level "./gradlew clean build".
After, you can either start the project from your IDE environment or deploy it on a docker container:
- IDE: Run the CodeChallengeApplication.kt main
- Docker: Again at root level, run in sequence the commands "docker-compose build" to build the image with the latest build and "docker-compose up" to run the image
Either way the application should start right away! 


## Usage

This API exposes one single endpoint that fetches based on the given username, all its repositories which are not forks and the correspondent branches information for each of the repositories.
This endpoint must have a header “Accept:application/json” and this is the only type accepted by it.
In case there's no user with such given name or the request is made with the wrong "Accept" header the application throws an error response with the format, {“status”: ${responseCode} “message”: ${whyHasItHappened}}
Endpoint:
http://localhost:8080/github-codechallenge/api/github/repositories/{username}


## Testing

The project tests run during the application build, but you can run it at any time by using the command "./gradlew test"


## API Documentation

The API is specified in the swagger.yaml file 


## Future Implementations

This project can be considered a proper POC, but in order for it to be used in a real production scenario it would benefit a lot from achieving these future goals: 
- Security
- Caching
- Cloud Infrastructure 
- CORS Restrictions
- Commit pipeline triggering 


## Personal Considerations

I've been wanting for sometime to implement something using webflux as I've interest in working in a reactive paradigm. 
This was my first time playing with a webflux (or any reactive framework for that matter) and it was a fun challenge!

However, I'm facing a few difficulties for which I searched a lot, but I was still not able to fix them:
- I'm not able to read the request body as the response object unless it is wrapped in a Mono, not sure why. So because starting from a Mono made me face even more
  problems when using the values inside the request body and returning a Flux, my working solution only accepts the json header as it should but the value inside 
  the request body is not a json but a string
- Had to adapt the integration test for validating the wrong header error. The error response is returned as it should during run time, but on the integration test,
  the right exception is thrown but not in the format
- Although it was not a direct requirement, I also faced problems configuring the swagger ui and the api-docs, so it was in sync with the API solution.
  I never had this issue before with Spring MVC, so I'm betting it misses some specific webflux configuration, although I already tried the swagger dedicated libs for swagger