package com.simbirsoft.task.domain.repository

import com.simbirsoft.task.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTaskList(): Flow<List<Task>>
    suspend fun addTask(task: Task)
    suspend fun getTaskById(taskId: Long): Task
    suspend fun removeTask(taskId: Long)
    suspend fun isNotEmpty(): Boolean
}