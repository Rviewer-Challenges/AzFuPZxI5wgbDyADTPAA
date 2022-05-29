package dev.alejo.mariomemory.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
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
import ir.samanjafari.easycountdowntimer.CountDownInterface
import java.util.*
import kotlin.collections.ArrayList


@SuppressLint("SetTextI18n", "NotifyDataSetChanged")
class MemoryBoardActivity : AppCompatActivity(),  OnBlockItemListener{

    private lateinit var binding: ActivityMemoryBoardBinding
    private var columns = EASY_COLUMNS
    private var totalBlocks = EASY_TOTAL_BLOCKS
    private var remainingPairs = EASY_TOTAL_BLOCKS/2
    private var movements = 0
    private var timerStarted = false
    private var blocks = ArrayList<BlockItem>()
    private var rndIdsUsed = ArrayList<Int>()
    private var blockSelected: BlockItem? = null
    private val adapter by lazy { BlockAdapter(this, blocks, this) }
    private val difficult by lazy { Prefs(this).getDifficult() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBlocksData()
    }

    @SuppressLint("Recycle")
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
        remainingPairs = totalBlocks/2
        val characters = resources.obtainTypedArray(R.array.characters)
        while ( blocks.size < totalBlocks) {
            val rndIndex = Random().nextInt(characters.length())
            val resID = characters.getResourceId(rndIndex, 0)
            if(rndIndex !in rndIdsUsed) {
                rndIdsUsed.add(rndIndex)
                blocks.add(BlockItem(blocks.size, resID))
                blocks.add(BlockItem(blocks.size, resID))
            }
        }
        blocks = ArrayList(blocks.shuffled())
        adapter.notifyDataSetChanged()
        initUI()
    }

    private fun initUI() {
        val typeFace = ResourcesCompat.getFont(this, R.font.mario_font)
        binding.countDown.setTypeFace(typeFace)
        binding.countDown.setOnTick(object: CountDownInterface {
            override fun onTick(time: Long) { timerStarted = true }

            override fun onFinish() {
                if(remainingPairs > 0)
                    showEndGameAlert(gameLost = true)
                else
                    showEndGameAlert(gameLost = false)
            }

        })
        binding.blocksRecycler.layoutManager = GridLayoutManager(
            this,
            columns,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.blocksRecycler.setHasFixedSize(true)
        binding.blocksRecycler.adapter = adapter
        updateValues()
    }

    private fun updateValues() {
        Picasso.get()
            .load(R.drawable.default_background)
            .into(binding.firstSelection)
        Picasso.get()
            .load(R.drawable.default_background)
            .into(binding.secondSelection)
        binding.movements.text = movements.toString()
        binding.remainingPairs.text = remainingPairs.toString()
        blockSelected = null
        adapter.notifyDataSetChanged()
        if(remainingPairs == 0)
            showEndGameAlert(gameLost = false)
    }

    private fun validateBlocksSelected(itemSelected: BlockItem) {
        movements++
        if(blockSelected!!.backImage == itemSelected.backImage) {
            remainingPairs--
            blocks[blocks.indexOf(blockSelected)].backImage = R.drawable.default_background
            blocks[blocks.indexOf(itemSelected)].backImage = R.drawable.default_background
        }
        updateValues()
    }

    private fun showBlockSelected(resourceId: Int, isFirstBlock: Boolean) {
        Picasso.get()
            .load(resourceId)
            .into(if(isFirstBlock) binding.firstSelection else binding.secondSelection)
    }

    private fun showEndGameAlert(gameLost: Boolean) {

    }

    override fun onBlockItemClick(item: BlockItem, position: Int) {
        if(!timerStarted)
            binding.countDown.startTimer()
        Handler(Looper.getMainLooper()).postDelayed({
            if(blockSelected == null) {
                blockSelected = item
                showBlockSelected(item.backImage, isFirstBlock = true)
            } else if(blockSelected != item) {
                showBlockSelected(item.backImage, isFirstBlock = false)
                Handler(Looper.getMainLooper()).postDelayed({validateBlocksSelected(item)}, 200)
            }
        }, 800)
    }

}