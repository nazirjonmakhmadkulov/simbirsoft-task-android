package com.simbirsoft.task.domain.useCases

import com.simbirsoft.task.domain.model.Task
import com.simbirsoft.task.domain.repository.TaskRepository
import javax.inject.Inject

class GetTaskByIdUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(taskId: Long): Task = taskRepository.getTaskById(taskId)
}