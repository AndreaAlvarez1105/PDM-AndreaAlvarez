package com.example.burbujas

//Fragmento principal

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.example.burbujas.model.BubbleTask
import kotlin.math.sqrt

class BubbleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create("sans-serif-medium", Typeface.BOLD)
    }
    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var bubbles: List<BubbleTask> = emptyList()
    private lateinit var physics: BubblePhysicsEngine
    private var physicsAnimator: ValueAnimator? = null

    var onBubbleClick: ((BubbleTask) -> Unit)? = null

    fun setBubbles(list: List<BubbleTask>) {
        bubbles = list
        if (width > 0 && height > 0) initPositions()
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        physics = BubblePhysicsEngine(w.toFloat(), h.toFloat())
        initPositions()
        startPhysics()
    }

    private fun initPositions() {
        val cx = width / 2f
        val cy = height / 2f
        bubbles.forEach { b ->
            b.x = cx + (Math.random() * 100 - 50).toFloat()
            b.y = cy + (Math.random() * 100 - 50).toFloat()
        }
    }

    private fun startPhysics() {
        physicsAnimator?.cancel()
        physicsAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = Long.MAX_VALUE
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                physics.update(bubbles)
                invalidate()
            }
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        bubbles.forEach { b ->
            // Sombra
            paint.color = Color.argb(40, 0, 0, 0)
            canvas.drawCircle(b.x + 4f, b.y + 6f, b.radius, paint)

            // Círculo principal con gradiente radial
            val shader = RadialGradient(
                b.x - b.radius * 0.3f,
                b.y - b.radius * 0.3f,
                b.radius * 1.2f,
                lightenColor(b.color, 0.3f),
                darkenColor(b.color, 0.15f),
                Shader.TileMode.CLAMP
            )
            paint.shader = shader
            canvas.drawCircle(b.x, b.y, b.radius, paint)
            paint.shader = null

            // Brillo superior
            val highlightPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.argb(60, 255, 255, 255)
            }
            canvas.drawCircle(
                b.x - b.radius * 0.25f,
                b.y - b.radius * 0.3f,
                b.radius * 0.45f,
                highlightPaint
            )

            // Texto
            val fontSize = (b.radius * 0.38f).coerceIn(10f, 20f)
            textPaint.textSize = fontSize
            val words = b.title.split(" ")
            val lineHeight = fontSize * 1.2f
            val startY = b.y - (words.size - 1) * lineHeight / 2f
            words.forEachIndexed { i, word ->
                canvas.drawText(word, b.x, startY + i * lineHeight + fontSize / 3f, textPaint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            val touched = bubbles.firstOrNull { b ->
                val dx = event.x - b.x
                val dy = event.y - b.y
                sqrt(dx * dx + dy * dy) <= b.radius
            }
            touched?.let { onBubbleClick?.invoke(it) }
        }
        return true
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        physicsAnimator?.cancel()
    }

    // Helpers de color
    private fun lightenColor(color: Int, factor: Float): Int {
        val r = (Color.red(color) + (255 - Color.red(color)) * factor).toInt().coerceIn(0, 255)
        val g = (Color.green(color) + (255 - Color.green(color)) * factor).toInt().coerceIn(0, 255)
        val b = (Color.blue(color) + (255 - Color.blue(color)) * factor).toInt().coerceIn(0, 255)
        return Color.rgb(r, g, b)
    }

    private fun darkenColor(color: Int, factor: Float): Int {
        val r = (Color.red(color) * (1 - factor)).toInt().coerceIn(0, 255)
        val g = (Color.green(color) * (1 - factor)).toInt().coerceIn(0, 255)
        val b = (Color.blue(color) * (1 - factor)).toInt().coerceIn(0, 255)
        return Color.rgb(r, g, b)
    }
}