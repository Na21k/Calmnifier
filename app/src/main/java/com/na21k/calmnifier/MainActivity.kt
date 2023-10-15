package com.na21k.calmnifier

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.android.gms.tasks.Tasks
import com.google.android.material.snackbar.Snackbar
import com.na21k.calmnifier.adapters.BreedsAdapter
import com.na21k.calmnifier.api.BreedsService
import com.na21k.calmnifier.api.ImagesService
import com.na21k.calmnifier.databinding.ActivityMainBinding
import com.na21k.calmnifier.helpers.enqueue
import com.na21k.calmnifier.model.BreedModel
import com.na21k.calmnifier.model.ImageModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAdapter: BreedsAdapter
    private val mBreedsService = RETROFIT.create<BreedsService>()
    private val mImagesService = RETROFIT.create<ImagesService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.appBar.appBar.title = getString(R.string.breeds_title)
        setSupportActionBar(mBinding.appBar.appBar)

        mAdapter = setUpRecyclerView()
        loadBreeds()
    }

    private fun setUpRecyclerView(): BreedsAdapter {
        val rv = mBinding.breedsList
        val adapter = BreedsAdapter()
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        return adapter
    }

    private fun loadBreeds() {
        mBreedsService.list()
            .enqueue(lifecycleOwner = this, object : Callback<List<BreedModel>> {

                override fun onResponse(
                    call: Call<List<BreedModel>>,
                    response: Response<List<BreedModel>>
                ) {
                    response.body()?.let {
                        mAdapter.items = it
                        loadBreedImages(it)
                    }
                }

                override fun onFailure(call: Call<List<BreedModel>>, t: Throwable) {
                    Snackbar.make(mBinding.root, t.message.toString(), Snackbar.LENGTH_INDEFINITE)
                        .show()
                }
            })
    }

    private fun loadBreedImages(breeds: List<BreedModel>) {
        val tasks = mutableListOf<Task<ImageModel>>()

        breeds.forEach {
            val task = loadBreedImage(it.referenceImageId)
            tasks += task
        }

        Tasks.whenAllSuccess<ImageModel>(tasks)
            .addOnCompleteListener {
                val imageModels = it.result
                mAdapter.itemImages = imageModels
            }
    }

    private fun loadBreedImage(imageId: String?): Task<ImageModel> {
        val completionSrc = TaskCompletionSource<ImageModel>()

        if (imageId == null) {
            completionSrc.setResult(null)
            return completionSrc.task
        }

        mImagesService.getById(imageId)
            .enqueue(this, object : Callback<ImageModel> {

                override fun onResponse(call: Call<ImageModel>, response: Response<ImageModel>) {
                    completionSrc.trySetResult(response.body())
                }

                override fun onFailure(call: Call<ImageModel>, t: Throwable) {
                    Snackbar.make(mBinding.root, t.message.toString(), Snackbar.LENGTH_INDEFINITE)
                        .show()
                }
            })

        return completionSrc.task
    }
}
