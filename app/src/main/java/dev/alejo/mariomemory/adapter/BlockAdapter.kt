package dev.alejo.mariomemory.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.alejo.mariomemory.R

class BlockAdapter(
    private val context: Context,
    private val blocks: ArrayList<BlockItem>
): RecyclerView.Adapter<BlockViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockViewHolder {
        return BlockViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.block_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BlockViewHolder, position: Int) {
        Picasso.get()
            .load(blocks[position].backImage)
            .into(holder.backBlockImage)
    }

    override fun getItemCount(): Int = blocks.size

}