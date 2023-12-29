package com.test.gitsubs.data.api.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


class GitApiServiceFactory {
    companion object {

        private const val BASE_URL = "https://api.github.com/"

        @Volatile
        private var INSTANCE: GitApi? = null

        fun getService(): GitApi {
            return INSTANCE ?: synchronized(this) {
                val instance = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(provideOkHttpClient())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
                    .create(GitApi::class.java)
                INSTANCE = instance
                instance
            }
        }

        private fun provideOkHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
            return client.build()
        }
    }

}