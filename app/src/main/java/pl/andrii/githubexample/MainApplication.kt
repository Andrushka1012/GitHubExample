package pl.andrii.githubexample

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import pl.andrii.githubexample.search.searchModule

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() = startKoin {
        androidLogger()
        androidContext(this@MainApplication)
        modules(
            listOf(
                appModule,
                searchModule
            )
        )
    }
}