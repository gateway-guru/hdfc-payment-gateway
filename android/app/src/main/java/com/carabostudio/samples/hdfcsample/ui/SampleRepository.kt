package com.carabostudio.samples.hdfcsample.ui

import com.carabostudio.samples.hdfcsample.common.CommonApiCall
import com.carabostudio.samples.hdfcsample.data.RazorpayOrderIdResponse
import com.carabostudio.samples.hdfcsample.data.SampleApi

class SampleRepository(private var sampleApi: SampleApi) : CommonApiCall() {

    // get order id
    suspend fun getOrderId(): RazorpayOrderIdResponse {

        // safe call
        return safeApiCall(
            call = {
                sampleApi.getOrderIdAsync(
                    mapOf(
                        "apiToken" to "4042a6ea5e2d4565b27002b2631663db",
                        "format" to "json",
                        "amt" to "1000",
                        "type" to ""
                    )
                ).await()
            },
            errorMessage = "Error Fetching Razorpay Order Id"
        ) as RazorpayOrderIdResponse

    }

}