package com.na21k.calmnifier

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.na21k.calmnifier.databinding.ActivityBreedBinding
import com.na21k.calmnifier.model.BreedModel

const val BREED_MODEL_EXTRA_KEY = "breedModelExtra"

class BreedActivity : BaseActivity() {

    private lateinit var mBinding: ActivityBreedBinding
    private lateinit var mViewModel: BreedActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityBreedBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.appBar.appBar.title = getString(R.string.breeds_title)
        setSupportActionBar(mBinding.appBar.appBar)
        enableUpNavigation(mBinding.appBar.appBar)
        makeNavBarLookNice()

        mViewModel = ViewModelProvider(this)[BreedActivityViewModel::class.java]
        display()
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
            mBinding.scrollView.setPadding(0, 0, 0, i.bottom)

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
        mViewModel.image.observe(this) {
            if (it != null) {
                Glide.with(this)
                    .load(it.url)
                    .error(R.drawable.ic_error_24)
                    .into(mBinding.catImage)
            }
        }
    }

    private fun display() {
        val model = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras?.getSerializable(BREED_MODEL_EXTRA_KEY, BreedModel::class.java)
        } else {
            intent.extras?.getSerializable(BREED_MODEL_EXTRA_KEY) as BreedModel
        }

        mBinding.appBar.appBar.title = model?.name

        setTextAndVisibility(mBinding.name, model?.name)
        setTextAndVisibility(mBinding.description, model?.description)
        setTextAndVisibility(mBinding.temperament, model?.temperament)
        setTextAndVisibility(mBinding.origin, model?.origin)

        val weight = model?.weight?.metric
        val lifeSpan = model?.lifeSpan

        if (weight != null) {
            val weightFormatted = getString(R.string.weight_formatted, weight)
            setTextAndVisibility(mBinding.weight, weightFormatted)
        }
        if (lifeSpan != null) {
            val lifeSpanFormatted = getString(R.string.life_span_formatted, lifeSpan)
            setTextAndVisibility(mBinding.lifeSpan, lifeSpanFormatted)
        }

        model?.referenceImageId?.let { mViewModel.loadImage(this, it) }
    }

    private fun setTextAndVisibility(textView: TextView, text: String?) {
        if (text == null) {
            textView.isVisible = false
        }

        textView.text = text
    }
}
