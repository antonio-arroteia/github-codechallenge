package com.tui.codechallenge.model

data class GithubRepoResponse(

    val repositoryName: String,

    val ownerLogin: String,

    val branches: List<Branch>
)

data class Branch(
    val branchName: String,

    val lastCommitSha: String
)