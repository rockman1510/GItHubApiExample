package com.example.githubapiexample.utils

import android.content.Context
import android.text.TextUtils
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapiexample.R

class CommonUtils {

    companion object {
        fun loadImageToView(context: Context, imageView: ImageView, url: String) {
            if (!TextUtils.isEmpty(url)) {
                Glide.with(context).load(url).into(imageView)
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(context, R.drawable.no_image_available)
                )
            }
        }

        fun isLastItemVisible(recyclerView: RecyclerView, itemCount: Int): Boolean {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val pos = layoutManager.findLastCompletelyVisibleItemPosition()
            return pos >= itemCount - 1
        }
    }
}