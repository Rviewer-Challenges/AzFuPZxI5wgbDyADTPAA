package dev.alejo.mariomemory.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import dev.alejo.mariomemory.R

class BlockViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val frontBlockImage = view.findViewById<ShapeableImageView>(R.id.front_block_image)
    val backBlockImage = view.findViewById<ShapeableImageView>(R.id.back_block_image)

}