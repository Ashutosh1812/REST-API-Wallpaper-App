package com.ashutosh.wallpaperapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ashutosh.wallpaperapp.R
import com.ashutosh.wallpaperapp.models.WallpaperModel
import com.ashutosh.wallpaperapp.utils.RotationTransform
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlin.math.roundToInt

class VerticalWallpapersAdapter(
    val context: Context,
    private val list: List<WallpaperModel>,
    private val listener: (wallModel: WallpaperModel) -> Unit
) :
    RecyclerView.Adapter<VerticalWallpapersAdapter.ItemViewHolder>() {
    private val cardRadius = context.resources.getDimension(R.dimen.card_corner_radius)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val wallpaperModelItem = list[position]

        val mlt = MultiTransformation(
            RotationTransform(wallpaperModelItem.rotation.toFloat() ?: 0f),
            CenterCrop(),
            RoundedCornersTransformation(
                cardRadius.roundToInt(),
                0
            )
        )

        Glide.with(context)
            .load(wallpaperModelItem.urls.small)
            .thumbnail()
            .transform(mlt)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.imageView)

        Log.d("TAG", "onBindViewHolder->: ${wallpaperModelItem.urls.small}")

        holder.itemView.setOnClickListener {
            listener(wallpaperModelItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_wall_grid, parent, false))
    override fun getItemCount()=list.size
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val imageView:ImageView = view.findViewById(R.id.image_view)
    }

}