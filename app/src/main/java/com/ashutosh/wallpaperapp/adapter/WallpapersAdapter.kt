package com.ashutosh.wallpaperapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ashutosh.wallpaperapp.databinding.ItemLayoutImageBinding
import com.ashutosh.wallpaperapp.models.WallpaperModel
import com.bumptech.glide.Glide

class WallpapersAdapter(
    val context: Context,
    private val list: List<WallpaperModel>,
    private val listener: (wallModel: WallpaperModel) -> Unit
) :
    RecyclerView.Adapter<WallpapersAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ItemLayoutImageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemLayoutImageBinding.inflate(LayoutInflater.from(context)))

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val wallpaperModelItem = list[position]
        Glide.with(context).load(wallpaperModelItem.urls.small).into(holder.binding.imageView)
        Log.d("TAG", "onBindViewHolder->: ${wallpaperModelItem.urls.small}")
        holder.itemView.setOnClickListener {

            listener(wallpaperModelItem)
        }
//        Toast.makeText(context, wallpaperModelItem.urls.small, Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int {
        return list.size
    }

}