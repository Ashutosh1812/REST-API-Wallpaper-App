package com.ashutosh.wallpaperapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ashutosh.wallpaperapp.databinding.CategoryItemLayoutBinding
import com.ashutosh.wallpaperapp.databinding.ItemLayoutImageBinding
import com.ashutosh.wallpaperapp.models.CategoryModel
import com.ashutosh.wallpaperapp.models.ColorModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class CategoriesAdapter(val context: Context, private val list: List<CategoryModel>) :
    RecyclerView.Adapter<CategoriesAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: CategoryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(CategoryItemLayoutBinding.inflate(LayoutInflater.from(context)))

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val wallpaperModelItem = list[position]
        val imageUrl = wallpaperModelItem.imageUrl
//        Glide.with(context).load("https://picsum.photos/id/${position + 1000}/200/300")
//            .diskCacheStrategy(
//                DiskCacheStrategy.NONE
//            ).into(holder.binding.imageView)
        Glide.with(context).load(imageUrl).into(holder.binding.imageView)
        holder.binding.categoryItemName.text = wallpaperModelItem.name
    }

    override fun getItemCount(): Int {
        return list.size
    }

}