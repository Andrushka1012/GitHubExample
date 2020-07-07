package pl.andrii.githubexample.repositoryDetails

import androidx.lifecycle.ViewModel
import pl.andrii.githubexample.infrastructure.networking.RequestLiveData
import pl.andrii.githubexample.infrastructure.networking.fetchData
import pl.andrii.githubexample.models.domainModels.RepositoryModel

class RepositoryDetailsViewModel(
    private val repositoryModel: RepositoryModel,
    private val detailsRepository: DetailsRepository
) : ViewModel() {

    val readMeRequestData = RequestLiveData<String>()

    init {
        fetchData(readMeRequestData) {
            detailsRepository.getReadMeForRepository(
                repositoryModel.owner,
                repositoryModel.name
            )
        }
    }
}