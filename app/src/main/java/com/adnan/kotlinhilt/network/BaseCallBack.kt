package com.adnan.kotlinhilt.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseCallBack<T>(private val call: Call<T>) : Callback<T> {

    // Abstract methods to be implemented by subclasses
    abstract fun onFinalSuccess(call: Call<T>, response: Response<T>)
    abstract fun onFinalFailure(errorString: String)

    override fun onResponse(call: Call<T>, response: Response<T>) {
        handleResponse(call, response)
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        onFinalFailure(getErrorFromThrowable(t))
    }

    // Validate the response and handle HTTP status codes
    private fun handleResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {

            // Check if the body is empty or contains invalid data
            val responseBody = response.body()?.toString()?.trim()
            if (responseBody.isNullOrEmpty()) {
                // Handle empty or invalid response body
                onFinalFailure("Empty or invalid response body.")
            } else {
                onFinalSuccess(call, response)
            }

        } else {
            // Handle errors based on status code
            val errorMessage = getErrorMessageForStatusCode(response.code(), response.message())
            onFinalFailure(errorMessage)
        }
    }

    // Return a user-friendly error message based on status code
    private fun getErrorMessageForStatusCode(code: Int, message: String): String {
        return when (code) {
            500 -> ErrorMessages.InternalServerError500.errorString
            400 -> ErrorMessages.BadRequest400.errorString
            404 -> ErrorMessages.NotFound404.errorString
            401 -> ErrorMessages.SessionExpired401.errorString
            422 -> message
            else -> message
        }
    }

    // Convert throwable exceptions to error messages
    private fun getErrorFromThrowable(t: Throwable): String {
        return when (t) {
            is UnknownHostException, is SocketException -> ErrorMessages.NoInternetError.errorString
            is SocketTimeoutException -> ErrorMessages.SocketTimeout.errorString
            else -> ErrorMessages.UnknownError.errorString
        }
    }
}
