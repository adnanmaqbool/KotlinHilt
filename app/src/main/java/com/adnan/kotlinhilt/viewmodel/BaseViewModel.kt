package com.adnan.kotlinhilt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adnan.kotlinhilt.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
open class BaseViewModel @Inject constructor()  : ViewModel()  {

    private val _isLoading: MutableLiveData<Event<Boolean>> by lazy { MutableLiveData<Event<Boolean>>() }

    fun getIsLoading(): LiveData<Event<Boolean>> = _isLoading
    // Public setter function to allow child classes to modify isLoading
    protected fun setIsLoading(isLoading: Boolean) {
        _isLoading.value = Event(isLoading)
    }

}