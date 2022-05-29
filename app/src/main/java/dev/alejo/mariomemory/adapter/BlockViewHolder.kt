package dev.alejo.mariomemory.adapter

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import dev.alejo.mariomemory.R

class BlockViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var isBlockSide = true
    private val blockItem = view.findViewById<MaterialCardView>(R.id.block_item)
    private val frontBlockImage = view.findViewById<ShapeableImageView>(R.id.front_block_image)
    private val backBlockImage = view.findViewById<ShapeableImageView>(R.id.back_block_image)

    fun bind(context: Context, item: BlockItem, position: Int, listener: OnBlockItemListener) {
        Picasso.get()
            .load(item.backImage)
            .into(backBlockImage)
        if(item.backImage == R.drawable.default_background) {
            frontBlockImage.visibility = View.GONE
            backBlockImage.visibility = View.VISIBLE
            backBlockImage.isEnabled = false
        }
        setUpAnimation(context, listener, item, position)
    }

    private fun setUpAnimation(
        context: Context,
        listener: OnBlockItemListener,
        item: BlockItem,
        position: Int
    ) {
        val animator by lazy { AnimatorInflater.loadAnimator(context, R.animator.flip) }
        blockItem.setOnClickListener {
            listener.onBlockItemClick(item, position)
            animator.setTarget(if (isBlockSide) frontBlockImage else backBlockImage)
            animator.start()
            animator.addListener(object: Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                    if (isBlockSide)
                        frontBlockImage.isEnabled = false
                    else
                        backBlockImage.isEnabled = false
                }

                override fun onAnimationEnd(p0: Animator?) {
                    if (isBlockSide) {
                        isBlockSide = false
                        frontBlockImage.isEnabled = true
                        frontBlockImage.visibility = View.GONE
                        backBlockImage.visibility = View.VISIBLE
                    } else {
                        backBlockImage.isEnabled = true
                        backBlockImage.visibility = View.GONE
                        frontBlockImage.visibility = View.VISIBLE
                    }
                }

                override fun onAnimationCancel(p0: Animator?) { }

                override fun onAnimationRepeat(p0: Animator?) { }

            })
        }
    }

}