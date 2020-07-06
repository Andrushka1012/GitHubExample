package pl.andrii.githubexample.search.list

import androidx.recyclerview.widget.RecyclerView
import pl.andrii.githubexample.databinding.ViewHolderRepositoryBinding
import pl.andrii.githubexample.models.domainModels.RepositoryModel

class RepositoryViewHolder(
    private val binding: ViewHolderRepositoryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(repository: RepositoryModel?) {
        binding.repository = repository
        binding.executePendingBindings()
    }
}