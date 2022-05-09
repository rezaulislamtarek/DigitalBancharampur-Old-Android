package com.diatomicsoft.digitalbancharampur.adapter.binding


import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.diatomicsoft.digitalbancharampur.util.BASEURL
import android.view.ViewGroup




@BindingAdapter("image")
fun setImage(view: ImageView, url : String){
    Glide.with(view).load(BASEURL+url).transition(DrawableTransitionOptions.withCrossFade()).into(view)
}

@BindingAdapter("drawableImage")
fun setDrawableImage(view: ImageView, image: Int){
    view.setImageResource(image)
}

@BindingAdapter("imageUri")
fun  setImageUri(view: ImageView, image: Uri){
    if(image !=null) view.setImageURI(image)
}