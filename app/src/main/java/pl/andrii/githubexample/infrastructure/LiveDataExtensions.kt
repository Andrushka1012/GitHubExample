package pl.andrii.githubexample.infrastructure

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

inline fun <reified T> LiveData<*>.filterIsInstance(): LiveData<T> =
    MediatorLiveData<T>()
        .apply {
            addSource(this@filterIsInstance) {
                if (it is T) value = it
            }
        }