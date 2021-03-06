package pl.andrii.githubexample.infrastructure.networking

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import pl.andrii.githubexample.infrastructure.filterIsInstance
import retrofit2.HttpException

class RequestLiveData<T : Any>(var data: MediatorLiveData<LoadingStatus<T>>) {
    constructor() : this(MediatorLiveData())

    val errorData = data.filterIsInstance<LoadingStatus.Error>()
        .map { it.exception }

    val hasError = data.map { it is LoadingStatus.Error }

    val isNotFoundError = data.map {
        it is LoadingStatus.Error && it.exception is HttpException && it.exception.code() == 404
    }

    val isLoading = data.map { it is LoadingStatus.Loading }

    val contentData = data
        .filterIsInstance<LoadingStatus.Success<T>>()
        .map { it.value }

    val hasContent = data.map { it is LoadingStatus.Success }
}
