package pl.andrii.githubexample.search

import pl.andrii.githubexample.infrastructure.networking.GitHubService
import pl.andrii.githubexample.infrastructure.networking.Mapper
import pl.andrii.githubexample.infrastructure.networking.Page
import pl.andrii.githubexample.models.domainModels.RepositoryModel

class SearchRepository(private val gitHubService: GitHubService, private val mapper: Mapper) {
    suspend fun search(searchQuery: String, page: Int, pageSize: Int): Page<RepositoryModel> {
        val pagedData = gitHubService.searchRepositories(
            query = searchQuery,
            page = page,
            pageSize = pageSize
        )

        return mapper.map(
            pagedData = pagedData,
            page = page,
            pageSize = pageSize
        )
    }

}