package pl.andrii.githubexample.models.domainModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepositoryModel(
    val id: Int,
    val name: String,
    val owner: String,
    val imageSrc: String,
    val programmingLanguage: String,
    val starsCount: Int,
    val watchersCount: Int,
    val license: String?,
    val openIssuesCount: Int,
    val cloneUrl: String,
    val createdAt: String,
    val forksCount: Int
): Parcelable