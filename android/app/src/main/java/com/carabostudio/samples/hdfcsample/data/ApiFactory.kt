package com.carabostudio.samples.hdfcsample.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiFactory {

    private const val baseUrl = "https://beta.airwaave.co.in/"

    // okhttp client
    private val okHttpClient = OkHttpClient().newBuilder().build()

    // retrofit
    private fun retrofit(): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(baseUrl)
        .build()

    // sample api
    val sampleApi: SampleApi = retrofit().create(SampleApi::class.java)


}