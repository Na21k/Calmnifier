package com.na21k.calmnifier

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.na21k.calmnifier.api.ImagesService
import com.na21k.calmnifier.helpers.enqueue
import com.na21k.calmnifier.model.ImageModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class BreedActivityViewModel(application: Application) : BaseViewModel(application) {
    val image: LiveData<ImageModel?> get() = _image
    private val _image = MutableLiveData<ImageModel?>(null)
    private val mImagesService = RETROFIT.create<ImagesService>()

    fun loadImage(lifecycleOwner: LifecycleOwner, imageId: String) {
        _isLoading.postValue(true)

        mImagesService.getById(imageId)
            .enqueue(lifecycleOwner, object : Callback<ImageModel> {

                override fun onResponse(call: Call<ImageModel>, response: Response<ImageModel>) {
                    _isLoading.postValue(false)
                    _image.postValue(response.body())
                }

                override fun onFailure(call: Call<ImageModel>, t: Throwable) {
                    _isLoading.postValue(false)
                    t.printStackTrace()
                    if (t.message != "Canceled") _error.postValue(t)
                }
            })
    }
}
