package com.example.burbujas

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.burbujas.model.BubbleTask
import com.example.burbujas.model.BubbleTask.domain.usecase.GetTasksUseCase   // ← Tu CRUD existente
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BubbleTaskViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase   // ← Inyecta tu caso de uso
) : ViewModel() {

    private val _bubbles = MutableLiveData<List<BubbleTask>>()
    val bubbles: LiveData<List<BubbleTask>> = _bubbles

    // Colores por prioridad
    private val priorityColors = mapOf(
        BubbleTask.Priority.LOW      to Color.parseColor("#5B8DEF"),  // Azul
        BubbleTask.Priority.MEDIUM   to Color.parseColor("#F5A623"),  // Naranja
        BubbleTask.Priority.HIGH     to Color.parseColor("#4CAF50"),  // Verde
        BubbleTask.Priority.CRITICAL to Color.parseColor("#E53935")   // Rojo
    )

    // Radio base por prioridad (px, escalar con density)
    private val priorityRadius = mapOf(
        BubbleTask.Priority.LOW      to 60f,
        BubbleTask.Priority.MEDIUM   to 75f,
        BubbleTask.Priority.HIGH     to 95f,
        BubbleTask.Priority.CRITICAL to 115f
    )

    fun loadTasks() {
        viewModelScope.launch {
            val tasks = getTasksUseCase()   // ← Llama tu CRUD
            _bubbles.value = tasks.map { task ->
                val priority = mapToPriority(task.priority)
                BubbleTask(
                    id       = task.id,
                    title    = task.title,
                    priority = priority,
                    color    = priorityColors[priority] ?: Color.GRAY,
                    radius   = priorityRadius[priority] ?: 70f
                )
            }
        }
    }

    private fun mapToPriority(raw: Int): BubbleTask.Priority = when (raw) {
        1    -> BubbleTask.Priority.LOW
        2    -> BubbleTask.Priority.MEDIUM
        3    -> BubbleTask.Priority.HIGH
        else -> BubbleTask.Priority.CRITICAL
    }
}
