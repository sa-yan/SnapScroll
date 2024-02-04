package com.sayan.imageapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ImageAdapter(private val context:Context, private val urlList:ArrayList<String>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.image_layout, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  urlList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(context)
            .load(urlList.get(position))
            .fitCenter()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(holder.image)
    }

    class ImageViewHolder(itemView :View):RecyclerView.ViewHolder(itemView){
        val image:ImageView = itemView.findViewById(R.id.imageList)

    }
}