package com.example.country.util

import android.annotation.SuppressLint
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.country.R

@SuppressLint("SuspiciousIndentation")
fun ImageView.loadUrl(url: String?) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadUrl.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(500)
        .placeholder(R.drawable.ic_image)
        .error(R.drawable.ic_error)
        .data(url)
        .target(this)
        .build()

        imageLoader.enqueue(request)
}