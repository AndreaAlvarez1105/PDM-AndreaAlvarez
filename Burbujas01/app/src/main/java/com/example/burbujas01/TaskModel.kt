package com.example.burbujas01

import java.util.UUID

// ── Enums ────────────────────────────────────────────────────────────────────

enum class TaskPriority { LOW, MEDIUM, HIGH }
enum class TaskStatus   { PENDING, IN_PROGRESS, DONE }

// ── Models ───────────────────────────────────────────────────────────────────

data class Task(
    val id: String = UUID.randomUUID().toString(),
    var title: String,
    var description: String = "",
    var priority: TaskPriority = TaskPriority.MEDIUM,
    var status: TaskStatus = TaskStatus.PENDING,
    var x: Float = 0.5f,   // normalized canvas position [0..1]
    var y: Float = 0.5f
) {
    val radiusDp: Int get() = when (priority) {
        TaskPriority.HIGH   -> 68
        TaskPriority.MEDIUM -> 52
        TaskPriority.LOW    -> 38
    }
    // En TaskModel.kt, dentro de la clase Task:
    val colorIndex: Int get() = when (status) {
        TaskStatus.PENDING -> 0
        TaskStatus.IN_PROGRESS -> 1
        TaskStatus.DONE -> 2
    }

}

data class TaskEdge(val fromId: String, val toId: String) {
    override fun equals(other: Any?): Boolean =
        other is TaskEdge && setOf(fromId, toId) == setOf(other.fromId, other.toId)
    override fun hashCode() = setOf(fromId, toId).hashCode()
}

// ── Graph ────────────────────────────────────────────────────────────────────

class TaskGraph {

    private val nodes = LinkedHashMap<String, Task>()
    private val edges = mutableSetOf<TaskEdge>()

    // Nodes
    fun addTask(task: Task)    { nodes[task.id] = task }
    fun updateTask(task: Task) { nodes[task.id] = task }
    fun removeTask(id: String) { nodes.remove(id); edges.removeAll { it.fromId == id || it.toId == id } }
    fun allTasks(): List<Task> = nodes.values.toList()
    fun getTask(id: String)    = nodes[id]

    // Edges
    fun addEdge(from: String, to: String): Boolean {
        if (from == to || !nodes.containsKey(from) || !nodes.containsKey(to)) return false
        return edges.add(TaskEdge(from, to))
    }
    fun removeEdge(from: String, to: String) { edges.remove(TaskEdge(from, to)) }
    fun hasEdge(from: String, to: String)    = edges.contains(TaskEdge(from, to))
    fun allEdges(): List<TaskEdge>           = edges.toList()
    fun edgesOf(id: String)                  = edges.filter { it.fromId == id || it.toId == id }
}