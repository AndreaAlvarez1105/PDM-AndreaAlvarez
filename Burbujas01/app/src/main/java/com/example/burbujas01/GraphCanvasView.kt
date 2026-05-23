package com.example.burbujas01


import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.core.content.ContextCompat
import kotlin.math.sqrt

class GraphCanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    // ── Callbacks set by Fragment/Activity ────────────────────────────────────
    var onTaskClick:       ((Task) -> Unit)? = null
    var onTaskLongClick:   ((Task) -> Unit)? = null
    var onTaskMoved:       ((String, Float, Float) -> Unit)? = null
    var onEdgeRequest:     ((String, String) -> Unit)? = null  // connect two tasks

    // ── State ─────────────────────────────────────────────────────────────────
    private var tasks: List<Task>     = emptyList()
    private var edges: List<TaskEdge> = emptyList()

    // Drag state
    private var draggedTaskId: String? = null
    private var dragOffsetX = 0f
    private var dragOffsetY = 0f

    // Edge-draw mode: tap first node, then second
    private var edgeModeFrom: String? = null
    var isEdgeMode: Boolean = false
        set(value) { field = value; edgeModeFrom = null; invalidate() }

    fun startEdgeFrom(taskId: String) {
        isEdgeMode = true
        edgeModeFrom = taskId
        invalidate()
    }

    // Scale
    private val density = resources.displayMetrics.density

    // ── Paints ────────────────────────────────────────────────────────────────
    private val edgePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#AAAAAA")
        strokeWidth = 2f * density
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(12f, 8f), 0f)
    }

    private val bubblePaint  = Paint(Paint.ANTI_ALIAS_FLAG)
    private val shadowPaint  = Paint(Paint.ANTI_ALIAS_FLAG)
    private val labelPaint   = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color     = Color.WHITE
        textAlign = Paint.Align.CENTER
        textSize  = 13f * density
        typeface  = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }
    private val edgeModePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color     = Color.WHITE
        style     = Paint.Style.STROKE
        strokeWidth = 3f * density
    }

    private val bubbleColors = listOf(
        Color.parseColor("#5B6FD4"),  // blue   – PENDING
        Color.parseColor("#E8863A"),  // orange – IN_PROGRESS
        Color.parseColor("#3DB87A")   // green  – DONE
    )

    // ── Public API ────────────────────────────────────────────────────────────

    fun submitData(tasks: List<Task>, edges: List<TaskEdge>) {
        this.tasks = tasks
        this.edges = edges
        invalidate()
    }

    // ── Drawing ───────────────────────────────────────────────────────────────

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (width == 0 || height == 0) return

        drawEdges(canvas)
        drawBubbles(canvas)
    }

    private fun drawEdges(canvas: Canvas) {
        for (edge in edges) {
            val from = tasks.find { it.id == edge.fromId } ?: continue
            val to   = tasks.find { it.id == edge.toId }   ?: continue
            canvas.drawLine(
                from.x * width,  from.y * height,
                to.x   * width,  to.y   * height,
                edgePaint
            )
        }
    }

    private fun drawBubbles(canvas: Canvas) {
        // Draw non-dragged first, dragged on top
        val sorted = tasks.sortedBy { if (it.id == draggedTaskId) 1 else 0 }

        for (task in sorted) {
            val cx = task.x * width
            val cy = task.y * height
            val r  = task.radiusDp * density

            // Shadow
            shadowPaint.color   = Color.parseColor("#33000000")
            canvas.drawCircle(cx + 4f, cy + 6f, r, shadowPaint)

            // Fill
            bubblePaint.color = bubbleColors[task.colorIndex]
            canvas.drawCircle(cx, cy, r, bubblePaint)

            // Edge-mode highlight ring
            if (isEdgeMode && task.id == edgeModeFrom) {
                canvas.drawCircle(cx, cy, r + 4f * density, edgeModePaint)
            }

            // Label (wrap at ~10 chars)
            drawCenteredLabel(canvas, task.title, cx, cy, r)
        }
    }

    private fun drawCenteredLabel(canvas: Canvas, text: String, cx: Float, cy: Float, radius: Float) {
        val maxChars = (radius / (labelPaint.textSize * 0.55f)).toInt().coerceAtLeast(6)
        val words    = text.split(" ")
        val lines    = mutableListOf<String>()
        var line     = ""
        for (word in words) {
            val candidate = if (line.isEmpty()) word else "$line $word"
            if (candidate.length <= maxChars) line = candidate
            else { if (line.isNotEmpty()) lines += line; line = word }
        }
        if (line.isNotEmpty()) lines += line

        val lineH  = labelPaint.textSize * 1.25f
        val startY = cy - (lines.size - 1) * lineH / 2f + labelPaint.textSize / 3f
        lines.forEachIndexed { i, l -> canvas.drawText(l, cx, startY + i * lineH, labelPaint) }
    }

    // ── Touch ─────────────────────────────────────────────────────────────────

    private val gestureDetector = GestureDetector(context,
        object : GestureDetector.SimpleOnGestureListener() {

            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                val task = taskAt(e.x, e.y) ?: return false
                if (isEdgeMode) {
                    val from = edgeModeFrom
                    if (from == null) {
                        edgeModeFrom = task.id
                        invalidate()
                    } else if (from != task.id) {
                        onEdgeRequest?.invoke(from, task.id)
                        edgeModeFrom = null
                        invalidate()
                    }
                } else {
                    onTaskClick?.invoke(task)
                }
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                taskAt(e.x, e.y)?.let { onTaskLongClick?.invoke(it) }
            }
        })

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                if (!isEdgeMode) {
                    val task = taskAt(event.x, event.y)
                    if (task != null) {
                        draggedTaskId = task.id
                        dragOffsetX   = event.x - task.x * width
                        dragOffsetY   = event.y - task.y * height
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val id = draggedTaskId ?: return true
                val nx = ((event.x - dragOffsetX) / width).coerceIn(0.05f, 0.95f)
                val ny = ((event.y - dragOffsetY) / height).coerceIn(0.05f, 0.95f)
                // Update local list for smooth rendering
                tasks = tasks.map { if (it.id == id) it.copy(x = nx, y = ny) else it }
                invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                val id = draggedTaskId
                if (id != null) {
                    val task = tasks.find { it.id == id }
                    if (task != null) onTaskMoved?.invoke(id, task.x, task.y)
                    draggedTaskId = null
                    animateBounce(id)
                }
            }
        }
        return true
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private fun taskAt(x: Float, y: Float): Task? =
        tasks.lastOrNull { task ->
            val r  = task.radiusDp * density
            val dx = x - task.x * width
            val dy = y - task.y * height
            sqrt(dx * dx + dy * dy) <= r
        }

    private fun animateBounce(taskId: String) {
        // Small scale-pulse via re-draw trick using a scale factor stored per task
        // (simple invalidate is enough for the basic effect)
        invalidate()
    }
}