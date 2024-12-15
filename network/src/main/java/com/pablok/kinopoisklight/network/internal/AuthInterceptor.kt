package com.pablok.kinopoisklight.network.internal

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class AuthInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("X-API-KEY", Constants.TOKEN)
        //requestBuilder.addHeader("accept", "application/json")
        return chain.proceed(requestBuilder.build())
    }
}