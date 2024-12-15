package com.pablok.kinopoisklight.network.internal.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pablok.kinopoisklight.network.KinopoiskApi
import com.pablok.kinopoisklight.network.internal.AuthInterceptor
import com.pablok.kinopoisklight.network.internal.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideKinopoiskApi(
        retrofit: Retrofit
    ): KinopoiskApi = retrofit.create(KinopoiskApi::class.java)

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        //.addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(Constants.BASE_URL)
        .build()

    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    fun provideOkhttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(AuthInterceptor())
            .addInterceptor(interceptor)
            .build()
    }
}