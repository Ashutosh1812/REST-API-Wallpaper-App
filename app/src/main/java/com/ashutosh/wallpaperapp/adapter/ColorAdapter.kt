package com.ashutosh.wallpaperapp.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ashutosh.wallpaperapp.databinding.ColorItemLayoutBinding
import com.ashutosh.wallpaperapp.models.CategoryModel
import com.ashutosh.wallpaperapp.models.ColorModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ColorAdapter(val context: Context, private val list: List<ColorModel>) :
    RecyclerView.Adapter<ColorAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ColorItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ColorItemLayoutBinding.inflate(LayoutInflater.from(context)))

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val wallpaperModelItem = list[position]
//        Glide.with(context).load("https://picsum.photos/id/${position + 1000}/200/300")
//            .diskCacheStrategy(
//                DiskCacheStrategy.NONE
//            ).into(holder.binding.imageView)
        val backgroundColor:String = wallpaperModelItem.value
        holder.binding.imageView.setBackgroundColor(Color.parseColor(backgroundColor))
//        holder.binding.categoryItemName.text = wallpaperModelItem.name
    }

    override fun getItemCount(): Int {
        return list.size
    }

}