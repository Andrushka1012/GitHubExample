package pl.andrii.githubexample.infrastructure.networking

import pl.andrii.githubexample.models.apiDto.RepositoriesPagedDto
import pl.andrii.githubexample.models.apiDto.RepositoryDto
import pl.andrii.githubexample.models.domainModels.RepositoryModel

// Mapper should map with typed arguments and profiles, but at current example its single functions for time saving
class Mapper {
    fun map(pagedData: RepositoriesPagedDto, page: Int, pageSize: Int): Page<RepositoryModel> = Page(
        results = pagedData.items.map(::map),
        totalCount = pagedData.total_count,
        page = page,
        pageSize = pageSize
    )

    private fun map(repositoryDto: RepositoryDto): RepositoryModel {
        return RepositoryModel(
            id = repositoryDto.id,
            name = repositoryDto.name,
            owner = repositoryDto.owner.login,
            imageSrc = repositoryDto.owner.avatar_url,
            programmingLanguage = repositoryDto.language ?: "",
            starsCount = repositoryDto.stargazers_count,
            watchersCount = repositoryDto.watchers_count,
            cloneUrl = repositoryDto.clone_url,
            createdAt = repositoryDto.created_at,
            forksCount = repositoryDto.forks_count,
            license = repositoryDto.license?.name,
            openIssuesCount = repositoryDto.open_issues_count
        )
    }

}