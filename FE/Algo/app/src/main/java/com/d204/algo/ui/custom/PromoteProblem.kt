package com.d204.algo.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.d204.algo.R
import pl.droidsonroids.gif.GifImageView

class PromoteProblem(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs) {
    private val gifImageView: GifImageView
    private val promoteProblemLayout: LinearLayoutCompat
    private val promoteProblemDifficulty: AppCompatImageView
    private val promoteProblemNumber: AppCompatTextView
    private val promoteProblemTitle: AppCompatTextView

    init {
        inflate(context, R.layout.custom_view_promote, this)

        gifImageView = findViewById(R.id.promote_problem_frame)
        promoteProblemLayout = findViewById(R.id.promote_problem)
        promoteProblemDifficulty = findViewById(R.id.promote_problem_difficulty)
        promoteProblemNumber = findViewById(R.id.promote_problem_number)
        promoteProblemTitle = findViewById(R.id.promote_problem_title)
    }

    fun setDifficultyImage(drawableResId: Int) {
        promoteProblemDifficulty.setImageResource(drawableResId)
    }

    fun setProblemNumber(number: String) {
        promoteProblemNumber.text = number
    }

    fun setProblemTitle(title: String) {
        promoteProblemTitle.text = title
    }
}