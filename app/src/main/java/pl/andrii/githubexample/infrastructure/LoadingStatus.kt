package pl.andrii.githubexample.infrastructure

sealed class LoadingStatus<out T : Any> {
    class Success<out T : Any>(val value: T) : LoadingStatus<T>()

    class Error(val exception: Exception) : LoadingStatus<Nothing>()

    object Loading : LoadingStatus<Nothing>()
}
