package com.simbirsoft.task.domain

import com.simbirsoft.task.domain.model.Task
import com.simbirsoft.task.domain.repository.TaskRepository
import com.simbirsoft.task.domain.useCases.GetTaskListUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertFailsWith

class GetTaskListUseCaseTest {

    @MockK
    private lateinit var taskRepository: TaskRepository
    private lateinit var getTasksUseCase: GetTaskListUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getTasksUseCase = GetTaskListUseCase(taskRepository)
    }

    @Test
    fun when_GetTasks_UseCase_Expected_Success() = runTest {
        val expectedTasks = listOf(
            Task(1, "Task 1", "Content 1", LocalDateTime.now(), LocalDateTime.now().plusHours(1), 0),
            Task(2, "Task 2", "Content 2", LocalDateTime.now(), LocalDateTime.now().plusHours(2), 1)
        )

        coEvery { taskRepository.getTaskList() } returns flowOf(expectedTasks)

        val result = getTasksUseCase.invoke().toList()

        assertEquals(1, result.size) // Flow emits one list
        assertEquals(expectedTasks, result.first())
    }

    @Test
    fun when_GetTasks_UseCase_Expected_EmptyList() = runTest {
        coEvery { taskRepository.getTaskList() } returns flowOf(emptyList())

        val result = getTasksUseCase.invoke().toList()

        assertEquals(1, result.size) // Flow emits one list
        assertTrue(result.first().isEmpty())
    }

    @Test
    fun when_GetTasks_UseCase_Expected_Error() = runTest {
        coEvery { taskRepository.getTaskList() } returns flow {
            throw RuntimeException("Error fetching tasks")
        }

        assertFailsWith<RuntimeException>("Error fetching tasks") {
            getTasksUseCase.invoke().collect {}
        }
    }
}