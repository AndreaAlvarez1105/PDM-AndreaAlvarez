package com.example.burbujas.model

//Modelo UI

import androidx.annotation.ColorInt

data class BubbleTask(
    val id: String,
    val title: String,
    val priority: Priority,
    @ColorInt val color: Int,
    val radius: Float = 0f,   // calculado dinámicamente
    var x: Float = 0f,        // posición actual
    var y: Float = 0f,
    var vx: Float = 0f,       // velocidad
    var vy: Float = 0f
) {
    enum class Priority { LOW, MEDIUM, HIGH, CRITICAL }
}