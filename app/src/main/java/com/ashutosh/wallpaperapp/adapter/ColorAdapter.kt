package com.ashutosh.wallpaperapp.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ashutosh.wallpaperapp.R
import com.ashutosh.wallpaperapp.databinding.CategoryHomeItemLayoutBinding
import com.ashutosh.wallpaperapp.databinding.ColorItemLayoutBinding
import com.ashutosh.wallpaperapp.databinding.HomeItemColorLayoutBinding
import com.ashutosh.wallpaperapp.models.CategoryModel
import com.ashutosh.wallpaperapp.models.ColorModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlin.math.roundToInt

class ColorAdapter(val context: Context, private val list: List<ColorModel>, private val listener: (colorName: String) -> Unit) :
    RecyclerView.Adapter<ColorAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: HomeItemColorLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(HomeItemColorLayoutBinding.inflate(LayoutInflater.from(context)))

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val wallpaperModelItem = list[position]
        val backgroundColor:String = wallpaperModelItem.value
        holder.binding.imageView.setBackgroundColor(Color.parseColor(backgroundColor))
        holder.binding.categoryItemName.text = wallpaperModelItem.name

        holder.itemView.setOnClickListener {
            Toast.makeText(context, wallpaperModelItem.name, Toast.LENGTH_SHORT).show()
            listener(wallpaperModelItem.name)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}