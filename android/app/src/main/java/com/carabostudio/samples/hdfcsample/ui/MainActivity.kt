package com.carabostudio.samples.hdfcsample.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.carabostudio.samples.hdfcsample.R
import com.razorpay.BaseRazorpay
import com.razorpay.PaymentResultListener
import com.razorpay.Razorpay
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    // tag
    private val tag = "MainActivity"

    // razorpay instance
    private lateinit var razorpay: Razorpay

    // view model
    private lateinit var mainViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // view
        setContentView(R.layout.activity_main)

        // hdfc
        razorpay = Razorpay(this, "rzp_test_es6L5RbwbfTHlh")

        // setup listeners
        setupListeners()

        // setup web view
        setupWebView()

        // optional
        // get payment methods
        razorpay.getPaymentMethods(object : BaseRazorpay.PaymentMethodsCallback {
            override fun onPaymentMethodsReceived(result: String?) {
                val paymentMethods = JSONObject(result)
                Log.d(tag, "Payment methods = $paymentMethods")
            }

            override fun onError(error: String?) {
                Log.d(tag, "Error fetching payment methods = $error")
            }
        })

    }

    // setup web view
    private fun setupWebView() {
        // setup web view
        myWebView.visibility = View.GONE
        razorpay.setWebView(myWebView)
    }

    // setup listeners
    @SuppressLint("SetTextI18n")
    private fun setupListeners() {

        // view model
        mainViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        // start payment button click listener
        startPayment.setOnClickListener {
            statusTextView.text = "Fetching Order id..."
            mainViewModel.getOrderId()
        }

        // listen live data
        mainViewModel.getLiveData().observeForever {
            if (it != null) {
                statusTextView.text = "Fetching Order id done."
                startPayment(it.orderid)
                Log.d(tag, "Razorpay Order Id Response = $it")
            } else {
                Log.d(tag, "Razorpay Order Id Response is null")
            }
        }

    }

    // start payment
    @SuppressLint("SetTextI18n")
    private fun startPayment(orderId: String) {
        try {

            statusTextView.text = "Payment Started..."

            val data = JSONObject()
            data.put("amount", 100000)
            data.put("order_id", orderId)
            data.put("email", "somecustomer@somesite.com")
            data.put("contact", "9823755551")
            data.put("currency", "INR")

            val notes = JSONObject()
            notes.put("custom_field", "abc")
            data.put("notes", notes)
            data.put("method", "netbanking")
            data.put("bank", "HDFC")

            // Make web view visible before submitting payment details
            myWebView.visibility = View.VISIBLE

            // submit
            razorpay.submit(data, object : PaymentResultListener {
                override fun onPaymentSuccess(razorpayPaymentId: String?) {
                    Log.d(tag, "Payment success with id $razorpayPaymentId")
                    statusTextView.text = "Payment success with id $razorpayPaymentId"
                }

                override fun onPaymentError(code: Int, description: String?) {
                    Log.d(tag, "Code $code with $description")
                    statusTextView.text = "Code $code with $description"
                }
            });

        } catch (e: Exception) {
            Log.d(tag, e.message)
        }
    }

}