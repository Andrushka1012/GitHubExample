package pl.andrii.githubexample.infrastructure

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import pl.andrii.githubexample.R

abstract class BaseFragment : Fragment() {

    val mainNavController: NavController
        get() = Navigation.findNavController(requireActivity(), R.id.appNavHostFragment)
}
