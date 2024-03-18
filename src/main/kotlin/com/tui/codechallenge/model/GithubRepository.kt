package com.tui.codechallenge.model

data class GithubRepository(
    val name: String,
    val owner: Owner,
    val fork: Boolean,
    val default_branch: String? = null,
)

data class Owner(val login: String){
    // Ensure a no-argument constructor is present
    constructor() : this("")
}
