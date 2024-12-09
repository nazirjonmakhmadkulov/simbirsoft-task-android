package com.simbirsoft.task.domain

import com.simbirsoft.task.domain.repository.TaskRepository
import com.simbirsoft.task.domain.useCases.RemoveTaskUseCase
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class RemoveTaskUseCaseTest {
    @MockK
    private lateinit var taskRepository: TaskRepository
    private lateinit var removeTaskUseCase: RemoveTaskUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        removeTaskUseCase = RemoveTaskUseCase(taskRepository)
    }

    @Test
    fun when_DeleteTask_UseCase_Expected_Success() {
        val taskId = 1L

        coEvery { taskRepository.removeTask(taskId) } just Runs

        runTest {
            removeTaskUseCase.invoke(taskId)
            coVerify { taskRepository.removeTask(taskId) }
        }
    }

    @Test
    fun when_DeleteTask_UseCase_Expected_Error() {
        val taskId = 1L

        coEvery { taskRepository.removeTask(taskId) } throws RuntimeException()

        runTest {
            assertFailsWith<RuntimeException> {
                removeTaskUseCase.invoke(taskId)
            }
        }
    }
}