package pl.andrii.githubexample.infrastructure.networking

import pl.andrii.githubexample.models.apiDto.RepositoriesPagedDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query(value = "q",encoded = true) query:String,
        @Query("page") page:Int,
        @Query("per_page") pageSize: Int
    ):RepositoriesPagedDto

}