package dev.alejo.mariomemory.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dev.alejo.mariomemory.App.Companion.EASY_COLUMNS
import dev.alejo.mariomemory.App.Companion.EASY_DIFFICULT
import dev.alejo.mariomemory.App.Companion.HARD_COLUMNS
import dev.alejo.mariomemory.App.Companion.HARD_DIFFICULT
import dev.alejo.mariomemory.App.Companion.MEDIUM_COLUMNS
import dev.alejo.mariomemory.App.Companion.MEDIUM_DIFFICULT
import dev.alejo.mariomemory.R
import dev.alejo.mariomemory.adapter.BlockAdapter
import dev.alejo.mariomemory.adapter.BlockItem
import dev.alejo.mariomemory.adapter.OnBlockItemListener
import dev.alejo.mariomemory.databinding.ActivityMemoryBoardBinding
import dev.alejo.mariomemory.preferences.Prefs

class MemoryBoardActivity : AppCompatActivity(),  OnBlockItemListener{

    private lateinit var binding: ActivityMemoryBoardBinding
    private var columns = EASY_COLUMNS
    private var blocks = ArrayList<BlockItem>()
    private val adapter by lazy { BlockAdapter(blocks, this) }
    private val difficult by lazy { Prefs(this).getDifficult() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBlocksData()
        initUI()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setBlocksData() {
        when(difficult) {
            EASY_DIFFICULT -> { columns = EASY_COLUMNS }
            MEDIUM_DIFFICULT -> { columns = MEDIUM_COLUMNS }
            HARD_DIFFICULT -> { columns = HARD_COLUMNS }
        }
        blocks = ArrayList<BlockItem>().apply {
            add(BlockItem(0, R.drawable.mario_thinking))
            add(BlockItem(1, R.drawable.mario_thinking))
            add(BlockItem(2, R.drawable.mario_thinking))
            add(BlockItem(3, R.drawable.mario_thinking))
            add(BlockItem(4, R.drawable.mario_thinking))
        }
        adapter.notifyDataSetChanged()
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

    override fun onBlockItemClick(item: BlockItem) {
        Log.e("Id->", item.imageId.toString())
    }

}