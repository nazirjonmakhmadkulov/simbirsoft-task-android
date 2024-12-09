package com.simbirsoft.task.presentation

import com.simbirsoft.task.domain.model.Task
import com.simbirsoft.task.domain.useCases.AddTaskUseCase
import com.simbirsoft.task.presentation.utils.MainDispatcherRule
import com.simbirsoft.task.presentation.addTask.AddTaskViewModel
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class AddTaskViewModelTest {

    @MockK
    private lateinit var addTaskUseCase: AddTaskUseCase

    private lateinit var addTaskViewModel: AddTaskViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        addTaskViewModel = AddTaskViewModel(addTaskUseCase)
    }

    @Test
    fun whenAddTask_Expected_Success() = runTest {
        val taskToAdd = Task(3, "Task 3", "Content 3", LocalDateTime.now(), LocalDateTime.now().plusHours(1), 2)
        coEvery { addTaskUseCase.invoke(taskToAdd) } just Runs

        addTaskViewModel.addTask(taskToAdd)

        coVerify { addTaskUseCase.invoke(taskToAdd) }
    }
}