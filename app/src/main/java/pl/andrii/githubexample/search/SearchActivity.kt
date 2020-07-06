package pl.andrii.githubexample.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.andrii.githubexample.R
import pl.andrii.githubexample.databinding.ActivitySearchBinding
import pl.andrii.githubexample.infrastructure.DebouncingQueryTextWatcher
import pl.andrii.githubexample.infrastructure.RepositoryDiffUtilCallback
import pl.andrii.githubexample.search.list.RepositoriesAdapter
import retrofit2.HttpException

class SearchActivity : AppCompatActivity() {
    private val searchViewModel by viewModel<SearchViewModel>()

    private val repositoriesAdapter by lazy {
        RepositoriesAdapter(
            RepositoryDiffUtilCallback()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityBinding()

        searchViewModel.apply {
            pagedListData.observe(this@SearchActivity, Observer(repositoriesAdapter::submitList))
            requestData.errorData.observe(this@SearchActivity, Observer(::displayError))
        }

        setupViews()
    }

    private fun initActivityBinding() {
        DataBindingUtil.setContentView<ActivitySearchBinding>(this, R.layout.activity_search)
            .also { binding ->
                binding.lifecycleOwner = this@SearchActivity
                binding.viewModel = searchViewModel
            }
    }

    private fun setupViews() {
        repositoriesRv.adapter = repositoriesAdapter
        searchViewEt.addTextChangedListener(
            DebouncingQueryTextWatcher(lifecycle, 750L, searchViewModel::search)
        )
    }

    private fun displayError(exception: Exception?) {
        Log.e("SearchActivity", "SearchActivity error:", exception)
        if (exception is HttpException) {
            val message = resources.getString(R.string.network_error, exception.code())
            Toast.makeText(this, message, Toast.LENGTH_LONG)
                .show()
        } else {
            Toast.makeText(this, R.string.something_went_wrong, Toast.LENGTH_LONG)
                .show()
        }
    }
}