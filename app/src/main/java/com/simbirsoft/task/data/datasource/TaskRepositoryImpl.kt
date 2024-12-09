package com.simbirsoft.task.data.datasource

import com.simbirsoft.task.data.dao.TaskDao
import com.simbirsoft.task.data.mapping.toEntity
import com.simbirsoft.task.data.mapping.toModel
import com.simbirsoft.task.domain.model.Task
import com.simbirsoft.task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    override fun getTaskList(): Flow<List<Task>> {
        return taskDao.getTaskList().map { taskDBList -> taskDBList.map { it.toModel() } }
    }

    override suspend fun addTask(task: Task) = taskDao.addTask(task.toEntity())
    override suspend fun getTaskById(taskId: Long) = taskDao.getTaskById(taskId).toModel()
    override suspend fun removeTask(taskId: Long) = taskDao.removeTask(taskId)
    override suspend fun isNotEmpty(): Boolean = taskDao.count() > 0
}