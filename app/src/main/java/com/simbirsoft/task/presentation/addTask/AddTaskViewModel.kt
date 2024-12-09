package com.simbirsoft.task.presentation.addTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simbirsoft.task.domain.model.Task
import com.simbirsoft.task.domain.useCases.AddTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase
) : ViewModel() {
    fun addTask(task: Task) = viewModelScope.launch { addTaskUseCase(task) }
}