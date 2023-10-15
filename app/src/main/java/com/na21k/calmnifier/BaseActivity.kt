package com.na21k.calmnifier

import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {

    protected fun enableUpNavigation(appBar: Toolbar) {
        appBar.setNavigationIcon(R.drawable.ic_arrow_back_24)
        appBar.setNavigationContentDescription(R.string.navigate_up_content_description)
        appBar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    protected open fun setListeners() {}
    protected open fun observeLiveData() {}

    protected fun switchLoadingMode(isLoading: Boolean, progressBar: ProgressBar) {
        if (isLoading) {
            disableButtons()
            progressBar.visibility = View.VISIBLE
        } else {
            enableButtons()
            progressBar.visibility = View.GONE
        }
    }

    protected open fun disableButtons() {}
    protected open fun enableButtons() {}
}
