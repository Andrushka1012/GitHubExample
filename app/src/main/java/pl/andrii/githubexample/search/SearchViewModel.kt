package pl.andrii.githubexample.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import pl.andrii.githubexample.infrastructure.networking.Page
import pl.andrii.githubexample.infrastructure.networking.PagedDataSourceFactory
import pl.andrii.githubexample.infrastructure.networking.PagedDataSourceFactory.Companion.DEFAULT_PAGE_SIZE
import pl.andrii.githubexample.infrastructure.networking.RequestLiveData
import pl.andrii.githubexample.models.domainModels.RepositoryModel

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {
    var searchQueryData = MutableLiveData("")
    private var currentSearchQuery: String? = null

    val requestData = RequestLiveData<List<RepositoryModel>>()

    private val dataSourceFactory = PagedDataSourceFactory(
        requestData,
        viewModelScope
    ) { page: Int, pageSize: Int ->
        val query = requireNotNull(searchQueryData.value)

        if (query.isNotBlank()) {
            currentSearchQuery = query
            searchRepository.search(requireNotNull(searchQueryData.value), page, pageSize)
        } else Page.emptyPage()
    }

    val pagedListData: LiveData<PagedList<RepositoryModel>> by lazy {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(DEFAULT_PAGE_SIZE)
            .setInitialLoadSizeHint(DEFAULT_PAGE_SIZE)
            .build()

        LivePagedListBuilder(dataSourceFactory, config)
            .build()
    }

    fun search(query: String) {
        if (query != currentSearchQuery && !searchQueryData.value.isNullOrBlank()) {
            dataSourceFactory.invalidate()
        }
    }
}