package com.na21k.calmnifier

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.na21k.calmnifier.adapters.BreedsAdapter
import com.na21k.calmnifier.api.BreedsService
import com.na21k.calmnifier.databinding.ActivityMainBinding
import com.na21k.calmnifier.model.BreedModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAdapter: BreedsAdapter
    private val mBreedsService = RETROFIT.create<BreedsService>()

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
            .enqueue(object : Callback<List<BreedModel>> {

                override fun onResponse(
                    call: Call<List<BreedModel>>,
                    response: Response<List<BreedModel>>
                ) {
                    response.body()?.let { mAdapter.items = it }
                }

                override fun onFailure(call: Call<List<BreedModel>>, t: Throwable) {
                    Snackbar.make(mBinding.root, t.message.toString(), Snackbar.LENGTH_INDEFINITE)
                        .show()
                }
            })
    }
}
