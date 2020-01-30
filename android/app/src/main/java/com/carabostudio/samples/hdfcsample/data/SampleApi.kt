package com.carabostudio.samples.hdfcsample.data

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SampleApi {

    // rzorpay order id api
    @FormUrlEncoded
    @POST("/ws/mobile/v5/generatororderrazorpay")
    fun getOrderIdAsync(@FieldMap dataMap: Map<String, String>): Deferred<Response<RazorpayOrderIdResponse>>
}