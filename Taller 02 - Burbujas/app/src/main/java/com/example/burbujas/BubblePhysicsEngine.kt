package com.example.burbujas


import com.example.burbujas.model.BubbleTask
import kotlin.math.*

class BubblePhysicsEngine(
    private var width: Float,
    private var height: Float
) {
    companion object {
        private const val DAMPING = 0.85f
        private const val REPULSION = 3000f
        private const val GRAVITY_CENTER = 0.015f
        private const val PADDING = 16f
    }

    fun update(bubbles: List<BubbleTask>) {
        applyBoundaryForces(bubbles)
        applyRepulsionForces(bubbles)
        applyCenterGravity(bubbles)
        integrate(bubbles)
    }

    private fun applyBoundaryForces(bubbles: List<BubbleTask>) {
        bubbles.forEach { b ->
            val left   = b.x - b.radius
            val right  = b.x + b.radius
            val top    = b.y - b.radius
            val bottom = b.y + b.radius

            if (left < PADDING)          b.vx += (PADDING - left) * 0.5f
            if (right > width - PADDING) b.vx -= (right - (width - PADDING)) * 0.5f
            if (top < PADDING)           b.vy += (PADDING - top) * 0.5f
            if (bottom > height - PADDING) b.vy -= (bottom - (height - PADDING)) * 0.5f
        }
    }

    private fun applyRepulsionForces(bubbles: List<BubbleTask>) {
        for (i in bubbles.indices) {
            for (j in i + 1 until bubbles.size) {
                val a = bubbles[i]
                val b = bubbles[j]
                val dx = b.x - a.x
                val dy = b.y - a.y
                val dist = sqrt(dx * dx + dy * dy).coerceAtLeast(1f)
                val minDist = a.radius + b.radius + PADDING

                if (dist < minDist) {
                    val force = REPULSION / (dist * dist)
                    val nx = dx / dist
                    val ny = dy / dist
                    a.vx -= nx * force
                    a.vy -= ny * force
                    b.vx += nx * force
                    b.vy += ny * force
                }
            }
        }
    }

    private fun applyCenterGravity(bubbles: List<BubbleTask>) {
        val cx = width / 2f
        val cy = height / 2f
        bubbles.forEach { b ->
            b.vx += (cx - b.x) * GRAVITY_CENTER
            b.vy += (cy - b.y) * GRAVITY_CENTER
        }
    }

    private fun integrate(bubbles: List<BubbleTask>) {
        bubbles.forEach { b ->
            b.vx *= DAMPING
            b.vy *= DAMPING
            b.x += b.vx
            b.y += b.vy
        }
    }

    fun resize(w: Float, h: Float) {
        width = w
        height = h
    }
}

