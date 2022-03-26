package com.ashutosh.wallpaperapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ashutosh.wallpaperapp.R
import com.ashutosh.wallpaperapp.databinding.CategoryImageLayoutBinding
import com.ashutosh.wallpaperapp.databinding.ItemLayoutImageBinding
import com.ashutosh.wallpaperapp.models.CategoryModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlin.math.roundToInt

class CategoriesAdapter(val context: Context, private val list: List<CategoryModel>) :
    RecyclerView.Adapter<CategoriesAdapter.ItemViewHolder>() {
    private val cardRadius = context.resources.getDimension(R.dimen.card_corner_radius)

    class ItemViewHolder(val binding: CategoryImageLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(CategoryImageLayoutBinding.inflate(LayoutInflater.from(context)))

    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val wallpaperModelItem = list[position]
        val imageUrl = wallpaperModelItem.imageUrl
        Glide.with(context).load("https://picsum.photos/id/${position + 1000}/200/300").transform(
            MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(cardRadius.roundToInt(), 0)
            )
        )
            .diskCacheStrategy(
                DiskCacheStrategy.NONE
            ).into(holder.binding.imageView)
        /*Glide.with(context).load(imageUrl)
            .thumbnail()
            .transform(
                MultiTransformation(
                    CenterCrop(),
                    RoundedCornersTransformation(cardRadius.roundToInt(), 0)
                )
            )
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.binding.imageView)*/

        holder.binding.wallNameTextView.text = wallpaperModelItem.name
    }

    override fun getItemCount(): Int {
        return list.size
    }

}