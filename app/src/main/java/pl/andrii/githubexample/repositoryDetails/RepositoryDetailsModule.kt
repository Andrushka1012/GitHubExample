package pl.andrii.githubexample.repositoryDetails

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.andrii.githubexample.models.domainModels.RepositoryModel

val repositoryDetailsModule = module {
    single { DetailsRepository(get()) }

    viewModel { (repositoryModel: RepositoryModel) -> RepositoryDetailsViewModel(repositoryModel, get()) }
}