package pl.andrii.githubexample.infrastructure

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import pl.andrii.githubexample.R

abstract class BaseFragment : Fragment {
    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    val mainNavController: NavController
        get() = Navigation.findNavController(requireActivity(), R.id.appNavHostFragment)
}
