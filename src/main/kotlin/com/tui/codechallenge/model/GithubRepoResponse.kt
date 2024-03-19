package com.tui.codechallenge.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "GitHub Repository Response")
data class GithubRepoResponse(

    @Schema(description = "Name of the repository")
    val repositoryName: String,

    @Schema(description = "Login of the repository owner")
    val ownerLogin: String,

    @Schema(description = "List of branches")
    val branches: List<Branch>
)

@Schema(description = "Branch")
data class Branch(

    @Schema(description = "Name of the branch")
    val branchName: String,

    @Schema(description = "SHA of the last commit on the branch")
    val lastCommitSha: String
)