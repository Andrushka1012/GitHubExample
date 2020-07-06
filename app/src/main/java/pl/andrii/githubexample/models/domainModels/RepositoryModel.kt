package pl.andrii.githubexample.models.domainModels

data class RepositoryModel(
    val id: Int,
    val name: String,
    val imageSrc: String,
    val programmingLanguage: String,
    val starsCount: Int,
    val watchersCount: Int
)