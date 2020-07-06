package pl.andrii.githubexample.infrastructure

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.*

class DebouncingQueryTextWatcher(
    lifecycle: Lifecycle,
    private val debouncePeriod: Long,
    private val onDebouncingQueryTextChange: (String) -> Unit
) : TextWatcher, LifecycleObserver {
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    private var watchJob: Job? = null

    init {
        lifecycle.addObserver(this)
    }

    override fun afterTextChanged(newText: Editable?) {
        watchJob?.cancel()
        watchJob = coroutineScope.launch {
            newText?.let {
                delay(debouncePeriod)
                onDebouncingQueryTextChange(newText.toString())
            }
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        // Do nothing
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        // Do nothing
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun destroy() {
        watchJob?.cancel()
    }
}