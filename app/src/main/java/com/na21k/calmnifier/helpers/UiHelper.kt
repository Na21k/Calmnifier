package com.na21k.calmnifier.helpers

import android.content.Context
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.na21k.calmnifier.R

fun generateNewFabLayoutParams(
    context: Context,
    fab: FloatingActionButton,
    bottomInset: Int,
    gravity: Int
): CoordinatorLayout.LayoutParams {
    val params = CoordinatorLayout.LayoutParams(
        fab.layoutParams as CoordinatorLayout.LayoutParams
    )

    params.gravity = gravity
    val pageMargin = context.resources.getDimension(R.dimen.page_margin).toInt()

    params.setMargins(
        params.leftMargin, params.topMargin, params.rightMargin,
        pageMargin + bottomInset
    )

    return params
}
