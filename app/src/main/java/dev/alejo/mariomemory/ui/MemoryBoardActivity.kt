package dev.alejo.mariomemory.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.alejo.mariomemory.R
import dev.alejo.mariomemory.adapter.BlockAdapter
import dev.alejo.mariomemory.adapter.BlockItem
import dev.alejo.mariomemory.adapter.OnBlockItemListener
import dev.alejo.mariomemory.databinding.ActivityMemoryBoardBinding
import dev.alejo.mariomemory.preferences.Prefs

class MemoryBoardActivity : AppCompatActivity(),  OnBlockItemListener{

    private lateinit var binding: ActivityMemoryBoardBinding
    private var blocks = ArrayList<BlockItem>()
    private val adapter by lazy { BlockAdapter(blocks, this) }
    private val difficult by lazy { Prefs(this).getDifficult() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        setData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setData() {
        blocks = ArrayList<BlockItem>().apply {
            add(BlockItem(0, R.drawable.mario_thinking))
            add(BlockItem(1, R.drawable.mario_thinking))
            add(BlockItem(2, R.drawable.mario_thinking))
            add(BlockItem(3, R.drawable.mario_thinking))
            add(BlockItem(4, R.drawable.mario_thinking))
        }
        adapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initUI() {
        blocks = ArrayList<BlockItem>().apply {
            add(BlockItem(0, R.drawable.mario_thinking))
            add(BlockItem(1, R.drawable.mario_thinking))
            add(BlockItem(2, R.drawable.mario_thinking))
            add(BlockItem(3, R.drawable.mario_thinking))
            add(BlockItem(4, R.drawable.mario_thinking))
        }
        adapter.notifyDataSetChanged()
        binding.blocksRecycler
            .layoutManager = GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false)
        binding.blocksRecycler.adapter = adapter
    }

    override fun onBlockItemClick(item: BlockItem) {
        Log.e("Id->", item.imageId.toString())
    }

}