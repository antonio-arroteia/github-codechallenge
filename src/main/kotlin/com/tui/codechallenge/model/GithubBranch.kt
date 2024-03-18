package com.tui.codechallenge.model

data class GithubBranch(
    val name: String,
    val commit: CommitInfo,
    val protected: Boolean,
)

data class CommitInfo(
    val sha: String,
    val url: String
)
