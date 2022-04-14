package com.ashutosh.wallpaperapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ashutosh.wallpaperapp.R
import com.ashutosh.wallpaperapp.databinding.ItemLayoutImageBinding
import com.ashutosh.wallpaperapp.models.WallpaperModel
import com.ashutosh.wallpaperapp.utils.RotationTransform
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import jp.co.cyberagent.android.gpuimage.util.Rotation
import jp.wasabeef.glide.transformations.BitmapTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlin.math.roundToInt

class WallpapersAdapter(
    val context: Context,
    private val list: List<WallpaperModel>,
    private val listener: (wallModel: WallpaperModel) -> Unit
) :
    RecyclerView.Adapter<WallpapersAdapter.ItemViewHolder>() {
    private val cardRadius = context.resources.getDimension(R.dimen.card_corner_radius)

    class ItemViewHolder(val binding: ItemLayoutImageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemLayoutImageBinding.inflate(LayoutInflater.from(context)))

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val wallpaperModelItem = list[position]

        val mlt = MultiTransformation(
//        RotationTransform(wallpaperModelItem.rotation.toFloat()),
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
            .into(holder.binding.imageView)
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