package com.sepia.pets.models

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.Date

data class Pet(
    val image_url:String,
    val title:String,
    val content_url:String,
    val date_added:Date
){
    companion object  {


        @JvmStatic
        @BindingAdapter("avatar_url")
        fun loadImage(imageView: ImageView?, imageURL: String?) {

            Log.e("imsgeurl",imageURL!!)
            Glide.with(imageView!!.context)
                .setDefaultRequestOptions(
                    RequestOptions()
                        .circleCrop()
                )
                .load(imageURL)

                .into(imageView)
        }

    }
}

