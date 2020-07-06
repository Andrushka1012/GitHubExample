package pl.andrii.githubexample

import okhttp3.OkHttpClient
import org.koin.dsl.module
import pl.andrii.githubexample.infrastructure.networking.Mapper
import pl.andrii.githubexample.infrastructure.networking.SecretInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    single { Mapper() }

    single { SecretInterceptor() }
    single {
        val secretInterceptor = get<SecretInterceptor>()

        OkHttpClient.Builder()
            .addInterceptor(secretInterceptor)
            .build()
    }

    single {
        val client = get<OkHttpClient>()
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

}