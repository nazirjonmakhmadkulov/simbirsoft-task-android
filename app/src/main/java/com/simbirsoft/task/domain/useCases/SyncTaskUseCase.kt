package com.simbirsoft.task.domain.useCases

import android.content.Context
import com.simbirsoft.task.domain.repository.ResourceRepository
import com.simbirsoft.task.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SyncTaskUseCase @Inject constructor(
    private val resourceRepository: ResourceRepository,
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(context: Context) = withContext(Dispatchers.IO) {
        if (!taskRepository.isNotEmpty()) {
            val tasks = resourceRepository.readJson(context)
            tasks.forEach { taskRepository.addTask(it) }
        }
    }
}