package com.raonsoft.matmanagement.extention

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.raonsoft.matmanagement.R

private val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

fun ImageView.clear() = Glide.with(context).clear(this)

@SuppressLint("CheckResult")
fun ImageView.load(
    url: String?,
    corner: Float = 0f,
    defaultImage : Int,
    scaleType: Transformation<Bitmap> = CenterInside()
) {
    val glideUrl = GlideUrl(
        url, LazyHeaders.Builder().addHeader(
            "User-Agent",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit / 537.36(KHTML, like Gecko) Chrome  47.0.2526.106 Safari / 537.36"
        ).build()
    )

    Glide.with(this).load(glideUrl)
        .placeholder(androidx.constraintlayout.widget.R.drawable.notification_bg_low)
        .error(defaultImage)
        .transition(DrawableTransitionOptions.withCrossFade(factory))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply {
            if (corner > 0) {
                transform(scaleType)
                transform(RoundedCorners(corner.fromDpToPx()))
            }
        }

        .into(this)
}