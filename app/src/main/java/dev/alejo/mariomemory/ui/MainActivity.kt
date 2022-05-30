package dev.alejo.mariomemory.ui

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.animation.addListener
import dev.alejo.mariomemory.App.Companion.EASY_DIFFICULT
import dev.alejo.mariomemory.App.Companion.HARD_DIFFICULT
import dev.alejo.mariomemory.App.Companion.MEDIUM_DIFFICULT
import dev.alejo.mariomemory.databinding.ActivityMainBinding
import dev.alejo.mariomemory.preferences.Prefs

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    @SuppressLint("Recycle")
    private fun initUI() {
        binding.playButton.setOnClickListener { hidePlayButton() }
        binding.easyButton.setOnClickListener {
            Prefs(this).saveDifficult(EASY_DIFFICULT)
            startGame()
        }
        binding.mediumButton.setOnClickListener {
            Prefs(this).saveDifficult(MEDIUM_DIFFICULT)
            startGame()
        }
        binding.hardButton.setOnClickListener {
            Prefs(this).saveDifficult(HARD_DIFFICULT)
            startGame()
        }
    }

    private fun hidePlayButton() {
        val animator = ObjectAnimator.ofFloat(binding.playButton, View.ALPHA, 0f)
        animator.duration = 100
        animator.start()
        animator.addListener(object: Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {
                binding.playButton.isEnabled = false
            }

            override fun onAnimationEnd(p0: Animator?) {
                binding.playButton.isEnabled = true
                binding.playButton.visibility = View.GONE
                binding.difficultContent.visibility = View.VISIBLE
                showDifficultOptions()
            }

            override fun onAnimationCancel(p0: Animator?) { }

            override fun onAnimationRepeat(p0: Animator?) { }

        })
    }

    private fun showDifficultOptions() {
        val easyOptionAnimator = ObjectAnimator.ofFloat(binding.easyButton, View.ALPHA, 1f)
        val mediumOptionAnimator = ObjectAnimator.ofFloat(binding.mediumButton, View.ALPHA, 1f)
        val hardOptionAnimator = ObjectAnimator.ofFloat(binding.hardButton, View.ALPHA, 1f)
        easyOptionAnimator.start()
        easyOptionAnimator.difficultListener(binding.easyButton)
        mediumOptionAnimator.start()
        mediumOptionAnimator.difficultListener(binding.easyButton)
        hardOptionAnimator.start()
        hardOptionAnimator.difficultListener(binding.easyButton)
    }

    private fun ObjectAnimator.difficultListener(view: View) {
        duration = 1000
        addListener { object: Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) { view.isEnabled = false }

            override fun onAnimationEnd(p0: Animator?) { view.isEnabled = true }

            override fun onAnimationCancel(p0: Animator?) {  }

            override fun onAnimationRepeat(p0: Animator?) {  }

        } }
    }

    private fun startGame() {
        startActivity(Intent(this, MemoryBoardActivity::class.java))
    }
}