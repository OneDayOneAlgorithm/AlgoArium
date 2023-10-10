package com.d204.algo.ui.home

import android.R.attr.height
import android.R.attr.width
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.LinearLayout
import com.d204.algo.R
import com.d204.algo.databinding.FragmentHomeBinding
import kotlin.random.Random

private const val TAG = "CoralGenerator"
class CoralGenerator(private val binding: FragmentHomeBinding) {
    private val handler = Handler(Looper.getMainLooper())
    private val constraintLayoutParam = binding.fragmentHomeConstraintLayout
    private var windowWidth = 0
    private var windowHeight = 0
    private var isWindowSizeSetting = false
    var isRunning = true

    init {
        binding.fragmentHomeBg.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (binding.fragmentHomeBg.width > 0 && binding.fragmentHomeBg.height > 0 && isWindowSizeSetting == false) {
                windowWidth = binding.fragmentHomeBg.width
                windowHeight = binding.fragmentHomeBg.height
                isWindowSizeSetting = true
            }
        }
    }

    private fun setImageView() {
        var randomX = Random.nextFloat() * windowWidth
        var randomY = -(windowHeight + SIZE).toFloat()
        val params = LinearLayout.LayoutParams(SIZE, SIZE)

        val coral = selectRandomCoralImg()
        val imageView = ImageView(constraintLayoutParam.context).apply {
            setBackgroundResource(coral)
            layoutParams = params
            x = randomX - SIZE
            y = randomY - SIZE
            elevation = Resources.getSystem().displayMetrics.density * 7 // 7dp
        }

        // 이동 애니메이션
        val translationAnimator = ObjectAnimator.ofFloat(imageView, "translationY", windowHeight - randomY, -SIZE.toFloat())
        // 회전 애니메이션
        val rotationAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f)

        val duration = Random.nextLong(3000, 20000)
        translationAnimator.duration = duration
        rotationAnimator.duration = duration

        // 애니메이션 세트 생성
        val animator = AnimatorSet()

        // 애니메이션을 동시에 실행하기 위해 설정
        animator.playTogether(translationAnimator, rotationAnimator)

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                constraintLayoutParam.removeView(imageView)
            }
        })

        constraintLayoutParam.addView(imageView)
        animator.start()
    }

    fun generateCoral() {
        Thread {
            while (isRunning) {
                Thread.sleep(500) // 0.5초마다 산호 생성

                handler.post {
                    setImageView()
                }
            }
        }.start()
    }

    private fun selectRandomCoralImg(): Int {
        val randomResourceId = when (Random.nextInt(5)) {
            0 -> R.drawable.coral0
            1 -> R.drawable.coral1
            2 -> R.drawable.coral2
            3 -> R.drawable.coral3
            else -> R.drawable.coral4
        }
        return randomResourceId
    }

    companion object {
        private const val SIZE = 200
    }
}
