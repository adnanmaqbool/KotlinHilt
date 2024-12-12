package com.adnan.kotlinhilt.repositary

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.adnan.kotlinhilt.model.responseModel.CategoryItem
import com.adnan.kotlinhilt.model.responseModel.Category
import com.adnan.kotlinhilt.network.ApiInterface
import com.adnan.kotlinhilt.network.BaseCallBack
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject


class HomeRepo @Inject constructor(private var apiInterface: ApiInterface) {

    private val gson: Gson = Gson()

    fun userLoginApiCall(id: Int?): MutableLiveData<Category> {
        val userLoginResponse = MutableLiveData<Category>()
        val call = apiInterface.homeApiCall(id)

        call.enqueue(object : BaseCallBack<ResponseBody>(call) {
            override fun onFinalSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    // Parse the response body if the request is successful
                    val type: Type = object : TypeToken<List<CategoryItem>>() {}.type
                    val jsonString = response.body()?.string()
                    val list = gson.fromJson<List<CategoryItem>>(jsonString, type)

                    // Update live data with success status
                    userLoginResponse.value = createCategoryResponse(list, true, "Success")
                } catch (e: Exception) {
                    Log.e("API Parsing Error", "Error parsing the response: ${e.message}")
                    // Update live data with error status
                    userLoginResponse.value = createCategoryResponse(emptyList(), false, "Error parsing the response")
                }
            }


            override fun onFinalFailure(errorString: String) {
                Log.e("API Failure", "API Call failed: $errorString")
                // Update live data with failure response
                userLoginResponse.value = createCategoryResponse(emptyList(), false, errorString ?: "Unknown error occurred")
            }
        })

        return userLoginResponse
    }

    /**
     * Helper function to create a Category response.
     *
     * @param categoryList The list of category items, empty in case of failure.
     * @param status Indicates success or failure.
     * @param message A message indicating the status or error details.
     * @return A Category object with updated details.
     */
    private fun createCategoryResponse(
        categoryList: List<CategoryItem>,
        status: Boolean,
        message: String
    ): Category {
        return Category().apply {
            this.categoryList = ArrayList(categoryList)
            this.status = status
            this.message = message
        }
    }
}
