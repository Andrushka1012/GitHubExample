package pl.andrii.githubexample.infrastructure.networking

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import pl.andrii.githubexample.BuildConfig


class SecretInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val url: HttpUrl = request.url().newBuilder()
            .addQueryParameter("client_id", BuildConfig.CLIENT_ID)
            .addQueryParameter("client_secret", BuildConfig.CLIENT_SECRET)
            .build()

        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

}