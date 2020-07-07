package pl.andrii.githubexample.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.andrii.githubexample.R
import pl.andrii.githubexample.databinding.FragmentSearchBinding
import pl.andrii.githubexample.infrastructure.BaseFragment
import pl.andrii.githubexample.infrastructure.DebouncingQueryTextWatcher
import pl.andrii.githubexample.infrastructure.RepositoryDiffUtilCallback
import pl.andrii.githubexample.search.list.RepositoriesAdapter
import retrofit2.HttpException

class SearchFragment : BaseFragment() {
    private val searchViewModel by viewModel<SearchViewModel>()

    private val repositoriesAdapter = RepositoriesAdapter(
        RepositoryDiffUtilCallback()
    ) { repository ->
        mainNavController.navigate(
            SearchFragmentDirections.actionSearchFragmentToRepositoryDetailsFragment(
                repository
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSearchBinding.inflate(inflater).also { binding ->
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = searchViewModel
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel.apply {
            pagedListData.observe(viewLifecycleOwner, Observer(repositoriesAdapter::submitList))
            requestData.errorData.observe(viewLifecycleOwner, Observer(::displayError))
        }

        setupViews()
    }

    private fun setupViews() {
        repositoriesRv.adapter = repositoriesAdapter
        searchViewEt.addTextChangedListener(
            DebouncingQueryTextWatcher(lifecycle, 750L, searchViewModel::search)
        )
    }

    private fun displayError(exception: Exception?) {
        Log.e("SearchFragment", "SearchFragment error:", exception)
        if (exception is HttpException) {
            val message = resources.getString(R.string.network_error, exception.code())
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG)
                .show()
        } else {
            Toast.makeText(requireContext(), R.string.something_went_wrong, Toast.LENGTH_LONG)
                .show()
        }
    }
}