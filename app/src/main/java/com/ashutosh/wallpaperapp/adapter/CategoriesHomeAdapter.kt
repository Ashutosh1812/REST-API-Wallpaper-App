package com.ashutosh.wallpaperapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ashutosh.wallpaperapp.R
import com.ashutosh.wallpaperapp.databinding.CategoryHomeItemLayoutBinding
import com.ashutosh.wallpaperapp.models.CategoryModel

class CategoriesHomeAdapter(val context: Context, private val list: List<CategoryModel>) :
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