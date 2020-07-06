package pl.andrii.githubexample.search

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.andrii.githubexample.infrastructure.networking.GitHubService
import retrofit2.Retrofit

val searchModule = module {
    single {
        val retrofit = get<Retrofit>()
        retrofit.create(GitHubService::class.java)
    }

    single { SearchRepository(get(), get()) }

    viewModel { SearchViewModel(get()) }
}