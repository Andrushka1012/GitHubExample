package pl.andrii.githubexample.infrastructure

import androidx.recyclerview.widget.DiffUtil
import pl.andrii.githubexample.models.domainModels.RepositoryModel

class RepositoryDiffUtilCallback : DiffUtil.ItemCallback<RepositoryModel>() {
    override fun areItemsTheSame(oldItem: RepositoryModel, newItem: RepositoryModel) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: RepositoryModel, newItem: RepositoryModel) = oldItem == newItem
}
