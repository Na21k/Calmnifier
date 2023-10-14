package com.na21k.calmnifier

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class CalmnifierAppGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder
            .setDefaultTransitionOptions(
                Drawable::class.java,
                DrawableTransitionOptions.withCrossFade(130)
            )
            .setDiskCache(
                InternalCacheDiskCacheFactory(
                    context, 150 * 1024 * 1024  //150 MiB
                )
            )
    }

    override fun isManifestParsingEnabled(): Boolean = false
}
