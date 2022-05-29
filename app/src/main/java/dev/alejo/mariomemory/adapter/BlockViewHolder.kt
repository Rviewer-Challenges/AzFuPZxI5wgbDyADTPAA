package dev.alejo.mariomemory.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import dev.alejo.mariomemory.R

class BlockViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val blockItem = view.findViewById<MaterialCardView>(R.id.block_item)
    val frontBlockImage = view.findViewById<ShapeableImageView>(R.id.front_block_image)
    private val backBlockImage = view.findViewById<ShapeableImageView>(R.id.back_block_image)

    fun bind(item: BlockItem, listener: OnBlockItemListener) {
        Picasso.get()
            .load(item.backImage)
            .into(backBlockImage)
        blockItem.setOnClickListener { listener.onBlockItemClick(item) }
    }

}