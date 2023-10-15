package com.na21k.calmnifier

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel(private val application: Application) : AndroidViewModel(application) {

    protected val _error = MutableLiveData<Throwable?>()
    protected val _isLoading = MutableLiveData(false)
    open val error: LiveData<Throwable?>
        get() = _error
    open val isLoading: LiveData<Boolean>
        get() = _isLoading

    open fun consumeError() {
        _error.postValue(null)
    }
}
