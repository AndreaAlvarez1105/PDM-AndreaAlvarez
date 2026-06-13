package com.example.lab_04_00073824.data.repository

import com.example.lab_04_00073824.data.local.TaskDao
import com.example.lab_04_00073824.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }
}
