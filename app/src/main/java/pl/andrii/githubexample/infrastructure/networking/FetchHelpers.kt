package pl.andrii.githubexample.infrastructure.networking

import android.util.Log
import androidx.lifecycle.liveData

inline fun <T : Any> fetchData(
    dataMediator: RequestLiveData<T>,
    crossinline dataBlock: suspend () -> T
) {
    dataMediator.data.apply {
        addSource(
            liveData {
                emit(LoadingStatus.Loading)
                try {
                    val data = dataBlock()
                    emit(LoadingStatus.Success(data))
                } catch (exception: Exception) {
                    Log.e("fetchData", "Fetch error:", exception)
                    emit(LoadingStatus.Error(exception))
                }
            }
        ) { value = it }
    }
}
