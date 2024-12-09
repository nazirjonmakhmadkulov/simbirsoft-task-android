package com.simbirsoft.task.presentation.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simbirsoft.task.domain.model.Task
import com.simbirsoft.task.domain.useCases.GetTaskListUseCase
import com.simbirsoft.task.domain.useCases.SyncTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val getTaskListUseCase: GetTaskListUseCase,
    private val syncTaskUseCase: SyncTaskUseCase,
) : ViewModel() {

    init {
        syncTasks(context)
    }

    private fun syncTasks(context: Context) = viewModelScope.launch {
        syncTaskUseCase.invoke(context)
    }

    val getTaskList: StateFlow<List<Task>> = getTaskListUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList(),
    )
}