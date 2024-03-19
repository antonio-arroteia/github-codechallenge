package com.tui.codechallenge.service

import com.tui.codechallenge.client.GithubClient
import com.tui.codechallenge.model.Branch
import com.tui.codechallenge.model.GithubBranch
import com.tui.codechallenge.model.GithubRepoResponse
import com.tui.codechallenge.model.GithubRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class GithubService(private val githubClient: GithubClient) {

    fun getUserRepositories(username: String): Flux<GithubRepoResponse> {
        return githubClient.getUserRepositories(username).filter { !it.fork }
            .flatMap { repo ->
                getRepositoryBranches(username, repo.name)
                    .collectList()
                    .map { branches ->
                        mapToGithubRepoResponse(repo, branches)
                    }
            }
    }

    private fun getRepositoryBranches(username: String, repositoryName: String): Flux<GithubBranch> {
        return githubClient.getUserBranches(username, repositoryName)
    }

    private fun mapToGithubRepoResponse(repo: GithubRepository, branches: List<GithubBranch>): GithubRepoResponse {
        return GithubRepoResponse(
            repositoryName = repo.name,
            ownerLogin = repo.owner.login,
            branches = branches.map { branch ->
                Branch(branchName = branch.name, lastCommitSha = branch.commit.sha)
            }
        )
    }

}