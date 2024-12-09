package com.simbirsoft.task.domain.useCases

import com.simbirsoft.task.domain.model.Task
import com.simbirsoft.task.domain.repository.TaskRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(task: Task) = taskRepository.addTask(task)
}