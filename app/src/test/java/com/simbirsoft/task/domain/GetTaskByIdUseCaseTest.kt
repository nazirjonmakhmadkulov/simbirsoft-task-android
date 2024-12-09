package com.simbirsoft.task.domain

import com.simbirsoft.task.domain.model.Task
import com.simbirsoft.task.domain.repository.TaskRepository
import com.simbirsoft.task.domain.useCases.GetTaskByIdUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class GetTaskByIdUseCaseTest {
    @MockK
    private lateinit var taskRepository: TaskRepository
    private lateinit var getTaskByIdUseCase: GetTaskByIdUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getTaskByIdUseCase = GetTaskByIdUseCase(taskRepository)
    }

    @Test
    fun when_GetTaskById_UseCase_Expected_Success() {
        val requestId = 0L
        val expectedData: Task = mockk {
            every { id } returns 0L
            every { title } returns "My task"
            every { content } returns "content"
            every { startDate } returns LocalDateTime.now()
            every { endDate } returns LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(1))
            every { color } returns 0
        }

        coEvery {
            taskRepository.getTaskById(requestId)
        } returns expectedData

        runTest {
            val result = getTaskByIdUseCase.invoke(taskId = requestId)
            assertEquals(expectedData, result)
        }
    }

    @Test
    fun when_GetTaskById_UseCase_Expected_Error() {
        val requestId = 170L

        coEvery {
            taskRepository.getTaskById(requestId)
        } throws RuntimeException()

        runTest {
            assertFailsWith<RuntimeException> {
                getTaskByIdUseCase.invoke(taskId = requestId)
            }
        }
    }
}