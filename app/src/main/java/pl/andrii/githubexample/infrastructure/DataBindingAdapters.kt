package pl.andrii.githubexample.infrastructure

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import pl.andrii.githubexample.R

@BindingAdapter("android:circularImage")
fun loadCircular(view: ImageView, value: String?) = value?.let {
   Glide.with(view)
       .load(value)
       .placeholder(R.drawable.ic_repository)
       .circleCrop()
       .transition(withCrossFade())
       .into(view)
}

@BindingAdapter("android:isVisible")
fun setVisibility(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}