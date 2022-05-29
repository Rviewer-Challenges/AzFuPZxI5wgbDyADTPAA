package dev.alejo.mariomemory.ui

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dev.alejo.mariomemory.App.Companion.EASY_COLUMNS
import dev.alejo.mariomemory.App.Companion.EASY_DIFFICULT
import dev.alejo.mariomemory.App.Companion.EASY_TOTAL_BLOCKS
import dev.alejo.mariomemory.App.Companion.HARD_COLUMNS
import dev.alejo.mariomemory.App.Companion.HARD_DIFFICULT
import dev.alejo.mariomemory.App.Companion.HARD_TOTAL_BLOCKS
import dev.alejo.mariomemory.App.Companion.MEDIUM_COLUMNS
import dev.alejo.mariomemory.App.Companion.MEDIUM_DIFFICULT
import dev.alejo.mariomemory.App.Companion.MEDIUM_TOTAL_BLOCKS
import dev.alejo.mariomemory.R
import dev.alejo.mariomemory.adapter.BlockAdapter
import dev.alejo.mariomemory.adapter.BlockItem
import dev.alejo.mariomemory.adapter.OnBlockItemListener
import dev.alejo.mariomemory.databinding.ActivityMemoryBoardBinding
import dev.alejo.mariomemory.preferences.Prefs
import java.util.*
import kotlin.collections.ArrayList


class MemoryBoardActivity : AppCompatActivity(),  OnBlockItemListener{

    private lateinit var binding: ActivityMemoryBoardBinding
    private var columns = EASY_COLUMNS
    private var totalBlocks = EASY_TOTAL_BLOCKS
    private var blocks = ArrayList<BlockItem>()
    private var rndIdsUsed = ArrayList<Int>()
    private val adapter by lazy { BlockAdapter(this, blocks, this) }
    private val difficult by lazy { Prefs(this).getDifficult() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBlocksData()
    }

    @SuppressLint("NotifyDataSetChanged", "Recycle")
    private fun setBlocksData() {
        when(difficult) {
            EASY_DIFFICULT -> {
                columns = EASY_COLUMNS
                totalBlocks = EASY_TOTAL_BLOCKS
            }
            MEDIUM_DIFFICULT -> {
                columns = MEDIUM_COLUMNS
                totalBlocks = MEDIUM_TOTAL_BLOCKS
            }
            HARD_DIFFICULT -> {
                columns = HARD_COLUMNS
                totalBlocks = HARD_TOTAL_BLOCKS
            }
        }
        val characters = resources.obtainTypedArray(R.array.characters)
        while ( blocks.size < totalBlocks) {
            val rndIndex = Random().nextInt(characters.length())
            val resID = characters.getResourceId(rndIndex, 0)
            if(rndIndex !in rndIdsUsed) {
                rndIdsUsed.add(rndIndex)
                blocks.add(BlockItem(blocks.size, resID))
                blocks.add(BlockItem(blocks.size + 1, resID))
            }
        }
        blocks = ArrayList(blocks.shuffled())
        adapter.notifyDataSetChanged()
        initUI()
    }

    private fun initUI() {
        binding.blocksRecycler.layoutManager = GridLayoutManager(
            this,
            columns,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.blocksRecycler.adapter = adapter
    }

    override fun onBlockItemClick(item: BlockItem, position: Int) {

    }

}