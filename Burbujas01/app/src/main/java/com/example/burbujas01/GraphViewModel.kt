package com.example.burbujas01

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GraphViewModel : ViewModel() {

    private val graph = TaskGraph()

    private val _tasks = MutableLiveData<List<Task>>(emptyList())
    val tasks: LiveData<List<Task>> = _tasks

    private val _edges = MutableLiveData<List<TaskEdge>>(emptyList())
    val edges: LiveData<List<TaskEdge>> = _edges

    // ── Task CRUD ─────────────────────────────────────────────────────────────

    fun addTask(title: String, description: String, priority: TaskPriority, x: Float = 0.5f, y: Float = 0.5f) {
        val task = Task(title = title, description = description, priority = priority, x = x, y = y)
        graph.addTask(task)
        refresh()
    }

    fun updateTask(task: Task) {
        graph.updateTask(task)
        refresh()
    }

    fun deleteTask(taskId: String) {
        graph.removeTask(taskId)
        refresh()
    }

    fun moveTask(taskId: String, x: Float, y: Float) {
        val task = graph.getTask(taskId) ?: return
        graph.updateTask(task.copy(x = x, y = y))
        refresh()
    }

    // ── Edge CRUD ─────────────────────────────────────────────────────────────

    fun addEdge(fromId: String, toId: String): Boolean {
        val added = graph.addEdge(fromId, toId)
        if (added) refresh()
        return added
    }

    fun removeEdge(fromId: String, toId: String) {
        graph.removeEdge(fromId, toId)
        refresh()
    }

    fun hasEdge(fromId: String, toId: String) = graph.hasEdge(fromId, toId)

    // ── Helpers ───────────────────────────────────────────────────────────────

    fun getTask(id: String) = graph.getTask(id)

    private fun refresh() {
        _tasks.value = graph.allTasks()
        _edges.value = graph.allEdges()
    }

    // ── Seed data (remove in production) ─────────────────────────────────────

    init {
        addTask("Pagar renta", description = "", priority = TaskPriority.HIGH,   x = 0.45f, y = 0.35f)
        addTask("Llamar al Dr.",   description = "",   priority = TaskPriority.HIGH,   x = 0.75f, y = 0.55f)
        addTask("Entregar tarea",   description = "",  priority = TaskPriority.MEDIUM, x = 0.18f, y = 0.22f)
        addTask("Leer correos",     description = "",  priority = TaskPriority.MEDIUM, x = 0.38f, y = 0.58f)
        addTask("Comprar mandado", description = "",   priority = TaskPriority.LOW,    x = 0.15f, y = 0.45f)
        addTask("Agendar dentista", description = "",  priority = TaskPriority.LOW,    x = 0.20f, y = 0.65f)
        addTask("Llamar al seguro", description = "",  priority = TaskPriority.MEDIUM, x = 0.72f, y = 0.22f)

        val ids = graph.allTasks().map { it.id }
        graph.addEdge(ids[0], ids[1])
        graph.addEdge(ids[0], ids[3])
        graph.addEdge(ids[2], ids[4])
        refresh()
    }
}