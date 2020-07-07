package pl.andrii.githubexample.repositoryDetails

import android.util.Base64
import pl.andrii.githubexample.infrastructure.networking.GitHubService
import retrofit2.HttpException

class DetailsRepository(private val gitHubService: GitHubService) {
    suspend fun getReadMeForRepository(owner: String, name: String): String {
        val response = gitHubService.fetchReadMeForRepository(
            owner = owner,
            repositoryName = name
        )

        if (response.isSuccessful) {
            val data = Base64.decode(response.body()?.content, Base64.DEFAULT)
            return String(data, Charsets.UTF_8)
        } else throw HttpException(response)
    }

}