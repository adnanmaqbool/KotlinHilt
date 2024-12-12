package com.adnan.kotlinhilt.network


import okhttp3.ResponseBody;
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ApiInterface {

    @Headers("Content-Type: application/json")
    @GET("urdurecepies_categories.php")  // category
    fun homeApiCall(@Header("Authorization") id: Int?): Call<ResponseBody>


}