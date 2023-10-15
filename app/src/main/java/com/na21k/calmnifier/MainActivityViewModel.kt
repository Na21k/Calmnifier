package com.na21k.calmnifier

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.android.gms.tasks.Tasks
import com.na21k.calmnifier.api.BreedsService
import com.na21k.calmnifier.api.ImagesService
import com.na21k.calmnifier.helpers.enqueue
import com.na21k.calmnifier.model.BreedModel
import com.na21k.calmnifier.model.ImageModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivityViewModel(application: Application) : BaseViewModel(application) {
    val breeds: LiveData<List<BreedModel>>
        get() = _breeds
    val breedImages: LiveData<List<ImageModel>>
        get() = _breedImages

    private val _breeds: MutableLiveData<List<BreedModel>> = MutableLiveData(listOf())
    private val _breedImages: MutableLiveData<List<ImageModel>> = MutableLiveData(listOf())
    private val mBreedsService = RETROFIT.create<BreedsService>()
    private val mImagesService = RETROFIT.create<ImagesService>()

    fun loadBreedsAndImages(lifecycleOwner: LifecycleOwner) {
        _isLoading.postValue(true)

        mBreedsService.list()
            .enqueue(lifecycleOwner, object : Callback<List<BreedModel>> {

                override fun onResponse(
                    call: Call<List<BreedModel>>,
                    response: Response<List<BreedModel>>
                ) {
                    response.body()?.let {
                        _isLoading.postValue(false)
                        _breeds.postValue(it)
                        loadBreedImages(lifecycleOwner, it)
                    }
                }

                override fun onFailure(call: Call<List<BreedModel>>, t: Throwable) {
                    _isLoading.postValue(false)
                    t.printStackTrace()
                    if (t.message != "Canceled") _error.postValue(t)
                }
            })
    }

    private fun loadBreedImages(lifecycleOwner: LifecycleOwner, breeds: List<BreedModel>) {
        val tasks = mutableListOf<Task<ImageModel>>()

        breeds.forEach { tasks += loadBreedImage(lifecycleOwner, it.referenceImageId) }

        Tasks.whenAllSuccess<ImageModel>(tasks)
            .addOnCompleteListener {
                val imageModels = it.result
                _breedImages.postValue(imageModels)
            }
    }

    private fun loadBreedImage(lifecycleOwner: LifecycleOwner, imageId: String?): Task<ImageModel> {
        val completionSrc = TaskCompletionSource<ImageModel>()

        if (imageId == null) {
            completionSrc.setResult(null)
            return completionSrc.task
        }

        mImagesService.getById(imageId)
            .enqueue(lifecycleOwner, object : Callback<ImageModel> {

                override fun onResponse(call: Call<ImageModel>, response: Response<ImageModel>) {
                    completionSrc.trySetResult(response.body())
                }

                override fun onFailure(call: Call<ImageModel>, t: Throwable) {
                    t.printStackTrace()
                    if (t.message != "Canceled") _error.postValue(t)
                }
            })

        return completionSrc.task
    }
}
