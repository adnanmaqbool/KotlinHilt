package com.adnan.kotlinhilt.viewmodel

import androidx.lifecycle.MutableLiveData
import com.adnan.kotlinhilt.model.responseModel.Category
import com.adnan.kotlinhilt.repositary.HomeRepo
import com.adnan.kotlinhilt.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private var loginRepo: HomeRepo) : BaseViewModel() {

    val categoryListResponse: MutableLiveData<Event<Category?>> by lazy { MutableLiveData<Event<Category?>>() }

    // Function to call the API
    fun listApi(id: Int) {
        // Indicate loading is true
        setIsLoading(true)

        try {
            // Call the API
            loginRepo.userLoginApiCall(id)
                .observeForever { data ->
                    // Handle response
                    data?.let { category ->
                        // Handle success
                        setIsLoading(false)
                        categoryListResponse.value = Event(category)
                    } ?: run {
                        // Handle null response
                        setIsLoading(false)
                        categoryListResponse.value = Event(createErrorCategory())
                    }
                }
        } catch (e: Exception) {
            // Handle exceptions
            setIsLoading(false)
            categoryListResponse.value = Event(createErrorCategory(e.message))
        }
    }

    // Helper function to create an error category
    private fun createErrorCategory(message: String? = null): Category {
        return Category().apply {
            categoryList = arrayListOf()
            status = false
            this.message = message ?: "Unknown error"
        }
    }
}
