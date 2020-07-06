package pl.andrii.githubexample.search.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import pl.andrii.githubexample.R
import pl.andrii.githubexample.databinding.ViewHolderRepositoryBinding
import pl.andrii.githubexample.infrastructure.RepositoryDiffUtilCallback
import pl.andrii.githubexample.models.domainModels.RepositoryModel

class RepositoriesAdapter(
    callback: RepositoryDiffUtilCallback
) : PagedListAdapter<RepositoryModel, RepositoryViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewHolderRepositoryBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.view_holder_repository,
            parent,
            false
        )

        return RepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}