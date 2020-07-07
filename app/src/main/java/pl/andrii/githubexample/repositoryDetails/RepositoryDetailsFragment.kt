package pl.andrii.githubexample.repositoryDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_repository_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import pl.andrii.githubexample.databinding.FragmentRepositoryDetailsBinding
import pl.andrii.githubexample.infrastructure.BaseFragment

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
    ): View? = FragmentRepositoryDetailsBinding.inflate(inflater)
        .also { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = repositoryDetailsViewModel
            binding.repository = args.repository
        }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repositoryDetailsViewModel.apply {
            readMeRequestData.contentData.observe(viewLifecycleOwner, Observer {
                readMeMdv.setMDText(it)
            })
        }
    }

}