package com.ashutosh.wallpaperapp

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat

class CustomTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {


    init {
        setTextColor(ContextCompat.getColor(context, R.color.textPrimary))
    }

}