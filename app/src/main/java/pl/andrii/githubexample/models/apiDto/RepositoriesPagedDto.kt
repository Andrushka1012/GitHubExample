package pl.andrii.githubexample.models.apiDto

data class RepositoriesPagedDto(
    val incomplete_results: Boolean,
    val items: List<RepositoryDto>,
    val total_count: Int
)