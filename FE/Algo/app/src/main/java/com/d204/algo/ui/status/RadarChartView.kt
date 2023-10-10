package com.d204.algo.ui.status

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.content.res.ResourcesCompat
import com.d204.algo.R
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

enum class CharacteristicType(val value: String) {
    WISDOM("지혜"),
    VITALITY("생명력"),
    STRENGTH("근력"),
    CHARISMA("카리스마"),
    LUCK("운"),
}

data class RadarChartData(
    val type: CharacteristicType,
    val value: Int,
) {
    // value값을 직접 바라보지 않고, 애니메이션처리되는 값을 반환하는 변수
    val animatedValue: Float
        get() {
            return value * scaleFactor
        }

    private var scaleFactor = 0f
    private var animator: ValueAnimator? = null

    fun animate(view: View, delay: Long) {
        animator?.cancel() // 중복으로 animate()를 호출하는 것을 막기 위해
        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 300 // 애니메이션 동작 시간
            startDelay = delay // 애니메이션 시작시간 delay
            interpolator = DecelerateInterpolator() // 애니메이션 속도가 점점 느려지도록
            addUpdateListener {
                scaleFactor = it.animatedValue as Float
                view.postInvalidateOnAnimation()
            }
        }
        animator!!.start()
    }

    fun cancelAnimate() {
        animator?.cancel()
    }
}

class RadarChartView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var dataList: ArrayList<RadarChartData>? = null
    private var sameLevelDataList: ArrayList<RadarChartData>? = null

    private val mainColor = 0x7FFF0000
    private val subColor = 0x7F018786

    // 5개의 특성을 갖도록 한다
    private var chartTypes = arrayListOf(
        CharacteristicType.WISDOM,
        CharacteristicType.VITALITY,
        CharacteristicType.STRENGTH,
        CharacteristicType.CHARISMA,
        CharacteristicType.LUCK,
    )

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    private val textPaint = TextPaint().apply {
        textSize = 56f
        textAlign = Paint.Align.CENTER
    }

    private val strokeTextPaint = TextPaint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        textSize = 56f
        strokeWidth = 2f
        color = Color.WHITE
    }

    private var path = Path()
    private var sameLevelPath = Path()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        val radian = PI.toFloat() * 2 / 5 // 360도를 5분할한 각만큼 회전시키 위해
        val step = 5 // 데이터 가이드 라인은 5단계로 표시한다
        val heightMaxValue = height / 2 * 0.7f // RadarChartView영역내에 모든 그림이 그려지도록 max value가 그려질 높이
        val heightStep = heightMaxValue / step // 1단계에 해당하는 높이
        val cx = width / 2f
        val cy = height / 2f

        // 0. 흰색 배경 그리기
        paint.color = Color.argb(88, 255, 255, 255)
        paint.style = Paint.Style.FILL

        path.reset()
        var startX = cx
        var startY = cy - heightMaxValue
        path.moveTo(startX, startY)

        repeat(chartTypes.size) {
            // 중심좌표를 기준으로 점(startX,startY)를 radian만큼씩 회전시킨 점(stopX, stopY)을 계산한다.
            val stopPoint = transformRotate(radian, startX, startY, cx, cy)
            path.lineTo(stopPoint.x, stopPoint.y)
            startX = stopPoint.x
            startY = stopPoint.y
        }
        canvas.drawPath(path, paint)

        paint.color = Color.BLUE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1f

        // 1. 단계별 가이드라인(5각형) 그리기
        for (i in 0..step) {
            var startX = cx
            var startY = (cy - heightMaxValue) + heightStep * i

            repeat(chartTypes.size) {
                // 중심좌표를 기준으로 점(startX,startY)를 radian만큼씩 회전시킨 점(stopX, stopY)을 계산한다.
                val stopPoint = transformRotate(radian, startX, startY, cx, cy)
                canvas.drawLine(startX, startY, stopPoint.x, stopPoint.y, paint)
                startX = stopPoint.x
                startY = stopPoint.y
            }
//            canvas.drawPath(path, paint)

            // 각 단계별 기준값 표시
//            if (i < step) {
//                val strValue = "${100 - 20 * i}"
//                textPaint.textAlign = Paint.Align.LEFT
//                canvas.drawText(
//                    strValue,
//                    startX + 10,
//                    textPaint.fontMetrics.getBaseLine(startY),
//                    textPaint,
//                )
//            }
        }

        // 3. 각 꼭지점 부근에 각 특성 문자열 표시하기
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.color = Color.BLACK
        textPaint.typeface = ResourcesCompat.getFont(context, R.font.dnf_bit)
        startX = cx
        startY = (cy - heightMaxValue) * 0.7f
        var r = 0f

        strokeTextPaint.textAlign = Paint.Align.CENTER
        strokeTextPaint.color = Color.WHITE
        strokeTextPaint.typeface = ResourcesCompat.getFont(context, R.font.dnf_bit)

        path.reset()
        sameLevelPath.reset()

        chartTypes.forEachIndexed { index, type ->
            val point = transformRotate(r, startX, startY, cx, cy)
            when (index) {
                0 -> textPaint.color = Color.YELLOW
                1 -> textPaint.color = Color.RED
                2 -> textPaint.color = Color.BLUE
                3 -> textPaint.color = Color.GREEN
                else -> textPaint.color = Color.MAGENTA
            }
            if (type == CharacteristicType.VITALITY) point.x += 25f
            canvas.drawText(
                type.value,
                point.x,
                textPaint.fontMetrics.getBaseLine(point.y),
                textPaint,
            )

            canvas.drawText(
                type.value,
                point.x,
                strokeTextPaint.fontMetrics.getBaseLine(point.y),
                strokeTextPaint,
            )

            // 전달된 데이터를 표시하는 path 계산
            dataList?.firstOrNull { it.type == type }?.animatedValue?.let { value ->
                val conValue = heightMaxValue * value / 100 // 차트크기에 맞게 변환
                val valuePoint = transformRotate(r, startX, cy - conValue, cx, cy)
                if (path.isEmpty) {
                    path.moveTo(valuePoint.x, valuePoint.y)
                } else {
                    path.lineTo(valuePoint.x, valuePoint.y)
                }
            }

            sameLevelDataList?.firstOrNull { it.type == type }?.animatedValue?.let { value ->
                val conValue = heightMaxValue * value / 100 // 차트크기에 맞게 변환
                val valuePoint = transformRotate(r, startX, cy - conValue, cx, cy)
                if (sameLevelPath.isEmpty) {
                    sameLevelPath.moveTo(valuePoint.x, valuePoint.y)
                } else {
                    sameLevelPath.lineTo(valuePoint.x, valuePoint.y)
                }
            }

            r += radian
        }

        // 4. 전달된 데에터를 표시하기
        path.close()
        sameLevelPath.close()

        paint.color = mainColor
        paint.style = Paint.Style.FILL
        canvas.drawPath(path, paint)

        paint.color = subColor
        canvas.drawPath(sameLevelPath, paint)

//        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.starfish)
//        Canvas(bitmap.copy(Bitmap.Config.ARGB_8888, true)).drawPath(path, paint)

//        canvas.drawBitmap(bitmap, null, 100F, paint)
    }

    fun setDataList(dataList: ArrayList<RadarChartData>) {
        if (dataList.isEmpty()) {
            return
        }
        // 이전에 처리되고 있던 애니메이션 모두 취소
        this.dataList?.forEach { data ->
            data.cancelAnimate()
        }
        this.dataList = dataList
        this.dataList?.forEachIndexed { index, data ->
            // 이전 애니메이션이 시작되고 30ms씩 대기했다가 처리되도록 delay 설정
            data.animate(this, (index * 30).toLong())
        }

        invalidate()
    }

    fun setSameLevelDataList(dataList: ArrayList<RadarChartData>) {
        // 이전에 처리되고 있던 애니메이션 모두 취소
        this.sameLevelDataList?.forEach { data ->
            data.cancelAnimate()
        }
        this.sameLevelDataList = dataList
        this.sameLevelDataList?.forEachIndexed { index, data ->
            // 이전 애니메이션이 시작되고 30ms씩 대기했다가 처리되도록 delay 설정
            data.animate(this, (index * 30).toLong())
        }

        invalidate()
    }

    // 점(x, y)를 특정 좌표(cx, cy)를 중심으로 radian만큼 회전시킨 점의 좌표를 반환
    private fun transformRotate(radian: Float, x: Float, y: Float, cx: Float, cy: Float): PointF {
        val stopX = cos(radian) * (x - cx) - sin(radian) * (y - cy) + cx
        val stopY = sin(radian) * (x - cx) + cos(radian) * (y - cy) + cy

        return PointF(stopX, stopY)
    }
}

// y좌표가 중심이 오도록 문자열을 그릴수 있도록하는 baseline좌표를 반환
fun Paint.FontMetrics.getBaseLine(y: Float): Float {
    val halfTextAreaHeight = (bottom - top) / 2
    return y - halfTextAreaHeight - top
}
