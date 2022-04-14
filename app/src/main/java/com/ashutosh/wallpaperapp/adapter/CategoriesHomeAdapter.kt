package com.ashutosh.wallpaperapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ashutosh.wallpaperapp.R
import com.ashutosh.wallpaperapp.databinding.CategoryHomeItemLayoutBinding
import com.ashutosh.wallpaperapp.models.CategoryModel
import com.ashutosh.wallpaperapp.models.WallpaperModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlin.math.roundToInt

class CategoriesHomeAdapter(val context: Context, private val list: List<CategoryModel>,private val listener: (categoryName: String) -> Unit) :
    RecyclerView.Adapter<CategoriesHomeAdapter.ItemViewHolder>() {
    private val cardRadius = context.resources.getDimension(R.dimen.card_corner_radius)

    class ItemViewHolder(val binding: CategoryHomeItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(CategoryHomeItemLayoutBinding.inflate(LayoutInflater.from(context)))

    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val wallpaperModelItem = list[position]
        val imageUrl = wallpaperModelItem.imageUrl
        holder.itemView.setOnClickListener {

            Toast.makeText(context, wallpaperModelItem.name, Toast.LENGTH_SHORT).show()
            listener(wallpaperModelItem.name)
        }
        Glide.with(context).load("https://picsum.photos/id/${position + 1000}/200/300").transform(
            MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(cardRadius.roundToInt(), 0)
            )
        )
            .diskCacheStrategy(
                DiskCacheStrategy.NONE
            ).into(holder.binding.imageView)

//        Glide.with(context).load("https://picsum.photos/id/${position + 1000}/200/300")
//            .diskCacheStrategy(
//                DiskCacheStrategy.NONE
//            ).into(holder.binding.imageView)
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

        holder.binding.categoryItemName.text = wallpaperModelItem.name
    }

    override fun getItemCount(): Int {
        return list.size
    }

}