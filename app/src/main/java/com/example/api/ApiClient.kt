package com.example.api

import com.example.flight.BuildConfig
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://api.schiphol.nl/public-flights/"
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {

        retrofit?.let {
            return it
        }?: run{
            retrofit =
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit!!
        }

    }
}
