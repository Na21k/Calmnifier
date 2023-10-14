package com.na21k.calmnifier

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.na21k.calmnifier.adapters.BreedsAdapter
import com.na21k.calmnifier.databinding.ActivityMainBinding
import com.na21k.calmnifier.model.BreedModel

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAdapter: BreedsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
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
        mAdapter.items =
            listOf(
                BreedModel(
                    "",
                    "Test breed",
                    "",
                    "",
                    "",
                    BreedModel.BreedWeightModel("", ""),
                    "",
                    ""
                )
            )
    }
}
