package com.carabostudio.samples.hdfcsample.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.carabostudio.samples.hdfcsample.data.ApiFactory
import com.carabostudio.samples.hdfcsample.data.RazorpayOrderIdResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    // parent job
    private val parentJob = Job()

    // coroutine context for job
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    // repository
    private val sampleRepository: SampleRepository =
        SampleRepository(ApiFactory.sampleApi)

    // coroutine scope
    private val scope = CoroutineScope(coroutineContext)

    // order id live data
    private var orderIdLiveData: MutableLiveData<RazorpayOrderIdResponse> = MutableLiveData()

    // get order id live data
    fun getLiveData(): LiveData<RazorpayOrderIdResponse> {
        return orderIdLiveData
    }

    // fetch order id
    fun getOrderId() {

        // fetch data from server
        scope.launch {

            // get order id
            val orderIdResponse = sampleRepository.getOrderId()
            orderIdLiveData.postValue(orderIdResponse)

        }

    }

}