package pl.andrii.githubexample.infrastructure.networking

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pl.andrii.githubexample.infrastructure.LoadingStatus

class PagedDataSource<T>(
    private val dataRequest: RequestLiveData<List<T>>,
    private val scope: CoroutineScope,
    private val dataBlock: suspend (page: Int, loadSize: Int) -> Page<T>
) : PageKeyedDataSource<Int, T>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        scope.launch {
            val data = fetchPage(1, params.requestedLoadSize)
            callback.onResult(data.results, null, data.nextPage)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        scope.launch {
            val data = fetchPage(params.key, params.requestedLoadSize)
            callback.onResult(data.results, data.nextPage)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        scope.launch {
            val pageKey = params.key
            if (pageKey > 0) {
                val data = fetchPage(pageKey, params.requestedLoadSize)
                callback.onResult(data.results, data.previousPage)
            } else {
                callback.onResult(emptyList(), null)
            }
        }
    }

    private suspend fun fetchPage(page: Int, requestedLoadSize: Int): Page<T> {
        return try {
            if (page == 1) {
                dataRequest.data.postValue(LoadingStatus.Loading)
            }

            val previousResults = if (dataRequest.data.value is LoadingStatus.Success && page != 1) {
                (dataRequest.data.value as LoadingStatus.Success<List<T>>).value
            } else emptyList()

            val data = dataBlock(page, requestedLoadSize)
            dataRequest.data.postValue(LoadingStatus.Success(previousResults + data.results))
            data
        } catch (exception: Exception) {
            dataRequest.data.postValue(LoadingStatus.Error(exception))
            Page(emptyList())
        }
    }
}

class PagedDataSourceFactory<T>(
    private val dataRequest: RequestLiveData<List<T>>,
    private val scope: CoroutineScope,
    private val dataBlock: suspend (page: Int, loadSize: Int) -> Page<T>
) : DataSource.Factory<Int, T>() {

    private lateinit var datasource: PagedDataSource<T>

    override fun create(): DataSource<Int, T> {
        datasource = PagedDataSource(
            dataRequest,
            scope,
            dataBlock
        )
        return datasource
    }

    fun invalidate() {
        if (::datasource.isInitialized) {
            datasource.invalidate()
        }
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
}