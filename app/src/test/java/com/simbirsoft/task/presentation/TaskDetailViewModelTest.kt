package com.simbirsoft.task.presentation

import com.simbirsoft.task.domain.model.Task
import com.simbirsoft.task.domain.useCases.GetTaskByIdUseCase
import com.simbirsoft.task.domain.useCases.RemoveTaskUseCase
import com.simbirsoft.task.presentation.utils.MainDispatcherRule
import com.simbirsoft.task.presentation.viewModels.TaskDetailsViewModel
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@OptIn(ExperimentalCoroutinesApi::class)
class TaskDetailViewModelTest {

    @MockK
    private lateinit var taskByIdUseCase: GetTaskByIdUseCase

    @MockK
    private lateinit var removeTaskUseCase: RemoveTaskUseCase

    private lateinit var taskDetailViewModel: TaskDetailsViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        taskDetailViewModel = TaskDetailsViewModel(
            taskByIdUseCase,
            removeTaskUseCase
        )
    }

    @Test
    fun when_load_task() = runTest {
        val expectedTask = Task(
            id = 37L,
            title = "My task",
            content = "content",
            startDate = LocalDateTime.now(),
            endDate = LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(1)),
            color = 0
        )

        coEvery {
            taskByIdUseCase.invoke(taskId = 37L)
        } returns expectedTask

        taskDetailViewModel.getTaskById(37L)
        val result = taskDetailViewModel.task.value
        assertEquals(expectedTask, result)
    }

    @Test
    fun whenRemoveTask_Expected_Success() = runTest {
        val taskId = 1L
        coEvery { removeTaskUseCase.invoke(taskId) } just Runs

        taskDetailViewModel.removeTask(taskId)

        coVerify { removeTaskUseCase.invoke(taskId) }
    }
}