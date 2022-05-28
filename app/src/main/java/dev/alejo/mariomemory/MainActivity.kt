package dev.alejo.mariomemory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import dev.alejo.mariomemory.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.playButton.setOnClickListener {
            binding.playButton.visibility = View.GONE
            binding.difficultContent.visibility = View.VISIBLE
        }
        binding.easyButton.setOnClickListener { startGame() }
        binding.mediumButton.setOnClickListener { startGame() }
        binding.hardButton.setOnClickListener { startGame() }
    }

    private fun startGame() {
        startActivity(Intent(this, MemoryBoardActivity::class.java))
    }
}