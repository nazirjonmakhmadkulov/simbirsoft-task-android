package com.simbirsoft.task.domain.useCases

import com.simbirsoft.task.domain.model.Task
import com.simbirsoft.task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskListUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    operator fun invoke(): Flow<List<Task>> = taskRepository.getTaskList()
}