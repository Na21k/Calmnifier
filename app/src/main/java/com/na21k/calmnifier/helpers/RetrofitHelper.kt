package com.na21k.calmnifier.helpers

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import retrofit2.Call
import retrofit2.Callback

fun <T> Call<T>.enqueue(lifecycleOwner: LifecycleOwner, callback: Callback<T>) {
    this.enqueue(callback)

    lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {

        override fun onDestroy(owner: LifecycleOwner) {
            cancelCall()
        }

        private fun cancelCall() {
            this@enqueue.cancel()
        }
    })
}
