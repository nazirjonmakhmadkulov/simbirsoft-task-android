package com.simbirsoft.task.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simbirsoft.task.domain.model.Task
import com.simbirsoft.task.domain.useCases.GetTaskByIdUseCase
import com.simbirsoft.task.domain.useCases.RemoveTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val removeTaskUseCase: RemoveTaskUseCase
) : ViewModel() {

    private val _task = MutableStateFlow<Task?>(null)
    val task: StateFlow<Task?> = _task.asStateFlow()

    fun getTaskById(taskId: Long) = viewModelScope.launch {
        val task = getTaskByIdUseCase(taskId)
        _task.value = task
    }

    fun removeTask(taskId: Long) = viewModelScope.launch {
        removeTaskUseCase(taskId)
    }
}