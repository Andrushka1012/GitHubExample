package pl.andrii.githubexample.repositoryDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_repository_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import pl.andrii.githubexample.R
import pl.andrii.githubexample.databinding.FragmentRepositoryDetailsBinding
import pl.andrii.githubexample.infrastructure.BaseFragment
import retrofit2.HttpException

class RepositoryDetailsFragment : BaseFragment() {
    private val args by navArgs<RepositoryDetailsFragmentArgs>()
    private val repositoryDetailsViewModel by viewModel<RepositoryDetailsViewModel> {
        parametersOf(
            args.repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentRepositoryDetailsBinding.inflate(inflater)
        .also { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = repositoryDetailsViewModel
            binding.repository = args.repository
        }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repositoryDetailsViewModel.readMeRequestData.apply {
            contentData.observe(viewLifecycleOwner, Observer {
                readMeMdv.setMDText(it)
            })
            errorData.observe(viewLifecycleOwner, Observer(::displayError))
        }
    }

    private fun displayError(exception: Exception?) {
        Log.e("RepositoryDetailsFragment", "RepositoryDetailsFragment error:", exception)
        if (exception !is HttpException || exception.code() != 404) {
            Toast.makeText(requireContext(), R.string.something_went_wrong, Toast.LENGTH_LONG)
                .show()
        }
    }

}