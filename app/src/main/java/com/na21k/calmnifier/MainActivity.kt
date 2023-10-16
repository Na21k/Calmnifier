package com.na21k.calmnifier

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.na21k.calmnifier.adapters.BreedsAdapter
import com.na21k.calmnifier.databinding.ActivityMainBinding
import com.na21k.calmnifier.helpers.generateNewFabLayoutParams
import com.na21k.calmnifier.model.BreedModel

class MainActivity : BaseActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAdapter: BreedsAdapter
    private lateinit var mViewModel: MainActivityViewModel
    private val mOnBreedActionListener = object : BreedsAdapter.OnItemActionListener {
        override fun itemOpen(model: BreedModel) {
            val intent = Intent(this@MainActivity, BreedActivity::class.java)
            intent.putExtra(BREED_MODEL_EXTRA_KEY, model)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.appBar.appBar.title = getString(R.string.breeds_title)
        setSupportActionBar(mBinding.appBar.appBar)
        makeNavBarLookNice()
        setListeners()
        mViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        mAdapter = setUpRecyclerView()
        loadBreeds()
        observeLiveData()
    }

    private fun makeNavBarLookNice() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.navigationBarColor = Color.TRANSPARENT

        ViewCompat.setOnApplyWindowInsetsListener(mBinding.root) { _, insets ->
            val insetsTypeMask =
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
            val i = insets.getInsets(insetsTypeMask)

            mBinding.root.setPadding(i.left, i.top, i.right, 0)
            mBinding.breedsList.setPadding(0, 0, 0, i.bottom)

            val newFabParams = generateNewFabLayoutParams(
                this, mBinding.favoritesFab, i.bottom,
                Gravity.BOTTOM or Gravity.END
            )
            mBinding.favoritesFab.layoutParams = newFabParams

            WindowInsetsCompat.CONSUMED
        }
    }

    override fun observeLiveData() {
        mViewModel.error.observe(this) {
            if (it != null) {
                Snackbar.make(mBinding.root, it.message.toString(), Snackbar.LENGTH_INDEFINITE)
                    .show()
                mViewModel.consumeError()
            }
        }
        mViewModel.isLoading.observe(this) { switchLoadingMode(it, mBinding.progressbar) }
        mViewModel.breeds.observe(this) { mAdapter.items = it }
        mViewModel.breedImages.observe(this) { mAdapter.itemImages = it }
    }

    override fun setListeners() {
        mBinding.favoritesFab.setOnClickListener {

        }
    }

    private fun setUpRecyclerView(): BreedsAdapter {
        val rv = mBinding.breedsList
        val adapter = BreedsAdapter(mOnBreedActionListener)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        return adapter
    }

    private fun loadBreeds() {
        mViewModel.loadBreedsAndImages(this)
    }
}
