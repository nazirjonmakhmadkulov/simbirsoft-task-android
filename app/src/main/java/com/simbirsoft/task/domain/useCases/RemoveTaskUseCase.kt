package com.simbirsoft.task.domain.useCases

import com.simbirsoft.task.domain.repository.TaskRepository
import javax.inject.Inject

class RemoveTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(taskId: Long) = taskRepository.removeTask(taskId)
}