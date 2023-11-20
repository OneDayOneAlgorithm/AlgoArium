package com.d204.algo.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.d204.algo.R
import com.d204.algo.databinding.CustomViewWantedBinding

class Wanted @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: CustomViewWantedBinding = CustomViewWantedBinding.inflate(LayoutInflater.from(context), this, true)
    private val glide = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .diskCacheStrategy(DiskCacheStrategy.DATA),
    )

//    init {
//        inflate(context, R.layout.custom_view_wanted, this)
//    }

    fun setWantedImage(resource: String) {
        glide.load(resource)
            .into(binding.wantedProfileImg)
    }

    fun setWantedName(name: String) {
        binding.wantedName.text = name
    }

    fun setWantedCost(cost: String) {
        binding.wantedCost.text = cost
    }

    fun getWantedName(): String{
        return binding.wantedName.text.toString()
    }
}
