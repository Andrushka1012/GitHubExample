package pl.andrii.githubexample.infrastructure

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import pl.andrii.githubexample.R

@BindingAdapter("android:circularImage")
fun loadCircular(view: ImageView, value: String?) = value?.let {
    view.load(value) {
        crossfade(true)
        diskCachePolicy(CachePolicy.ENABLED)
        placeholder(R.drawable.ic_repository)
        transformations(CircleCropTransformation())
    }
}

@BindingAdapter("android:isVisible")
fun setVisibility(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}