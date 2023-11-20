package com.d204.algo.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import com.d204.algo.R
import com.google.android.material.card.MaterialCardView

class RecommendProblemView(context: Context, attrs: AttributeSet? = null) : MaterialCardView(context, attrs) {
    private val frameImageView: AppCompatImageView
    private val recommendProblemLayout: LinearLayoutCompat
    private val recommendProblemDifficulty: AppCompatImageView
    private val recommendProblemNumber: AppCompatTextView
    private val recommendProblemTitle: AppCompatTextView

    init {
        inflate(context, R.layout.custom_view_problem, this)

        frameImageView = findViewById(R.id.recommend_problem_frame)
        recommendProblemLayout = findViewById(R.id.recommend_problem)
        recommendProblemDifficulty = findViewById(R.id.recommend_problem_difficulty)
        recommendProblemNumber = findViewById(R.id.recommend_problem_number)
        recommendProblemTitle = findViewById(R.id.recommend_problem_title)

        // xml에서 android: 패키지명을 가진 속성은 커스텀뷰 init에서 초기화해야 적용됨
        // app: 패키지명을 가진 속성은 xml에 속성을 넣어도 적용됨, 라이프사이클이 다른 걸로 보임
        this.elevation = 0f
        this.backgroundTintList = ContextCompat.getColorStateList(context, R.color.real_transparent)
    }

    fun setDifficultyImage(drawableResId: Int) {
        recommendProblemDifficulty.setImageResource(drawableResId)
    }

    fun setProblemNumber(number: String) {
        recommendProblemNumber.text = number
    }

    fun setProblemTitle(title: String) {
        recommendProblemTitle.text = title
        recommendProblemTitle.isSelected = true
    }
}
