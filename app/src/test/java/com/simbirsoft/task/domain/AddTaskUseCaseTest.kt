package com.simbirsoft.task.domain

import com.simbirsoft.task.domain.model.Task
import com.simbirsoft.task.domain.repository.TaskRepository
import com.simbirsoft.task.domain.useCases.AddTaskUseCase
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class AddTaskUseCaseTest {
    @MockK
    private lateinit var taskRepository: TaskRepository
    private lateinit var addTaskUseCase: AddTaskUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        addTaskUseCase = AddTaskUseCase(taskRepository)
    }

    @Test
    fun when_AddTask_UseCase_Expected_Success() {
        val taskToAdd: Task = mockk {
            every { id } returns 1L
            every { title } returns "New Task"
            every { content } returns "New Content"
            every { startDate } returns LocalDateTime.now()
            every { endDate } returns LocalDateTime.now().plusHours(2)
            every { color } returns 1
        }

        coEvery { taskRepository.addTask(taskToAdd) } just Runs

        runTest {
            addTaskUseCase.invoke(taskToAdd)
            coVerify { taskRepository.addTask(taskToAdd) }
        }
    }

    @Test
    fun when_AddTask_UseCase_Expected_Error() {
        val taskToAdd: Task = mockk {
            every { id } returns 1L
            every { title } returns "New Task"
            every { content } returns "New Content"
            every { startDate } returns LocalDateTime.now()
            every { endDate } returns LocalDateTime.now().plusHours(2)
            every { color } returns 1
        }

        coEvery { taskRepository.addTask(taskToAdd) } throws RuntimeException()

        runTest {
            assertFailsWith<RuntimeException> {
                addTaskUseCase.invoke(taskToAdd)
            }
        }
    }
}
