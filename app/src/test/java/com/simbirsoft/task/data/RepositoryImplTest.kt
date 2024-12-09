package com.simbirsoft.task.data

import com.simbirsoft.task.data.dao.TaskDao
import com.simbirsoft.task.data.datasource.TaskRepositoryImpl
import com.simbirsoft.task.data.mapping.toEntity
import com.simbirsoft.task.data.mapping.toModel
import com.simbirsoft.task.data.model.TaskEntity
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryImplTest {

    @MockK
    private lateinit var dao: TaskDao

    private lateinit var taskRepositoryImpl: TaskRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        taskRepositoryImpl = TaskRepositoryImpl(dao)
    }

    private val expectedNoteResponse = mockk<TaskEntity> {
        every { id } returns 37L
        every { title } returns "My task"
        every { content } returns "content"
        every { startDate } returns 100
        every { endDate } returns 200
        every { color } returns 0
    }

    @Test
    fun when_GetTaskById_Expected_Success() {
        val requestId = 37L
        val expectedResult = TaskEntity(37, "My task", "content", 100, 200, 0)
        coEvery { dao.getTaskById(requestId) } returns expectedNoteResponse
        runTest {
            val result = taskRepositoryImpl.getTaskById(requestId).toEntity()
            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun when_GetTaskById_Expected_Error() {
        val requestId = 170L
        coEvery { dao.getTaskById(requestId) } throws Throwable()
        runTest {
            assertFailsWith<Throwable> {
                taskRepositoryImpl.getTaskById(requestId)
            }
        }
    }

    @Test
    fun when_AddTask_Expected_Success() {
        val taskToAdd = TaskEntity(37, "New Task", "New Content", 100, 200, 0)
        coEvery { dao.addTask(taskToAdd) } just Runs
        runTest {
            taskRepositoryImpl.addTask(taskToAdd.toModel())
            coVerify { dao.addTask(taskToAdd) }
        }
    }

    @Test
    fun when_AddTask_Expected_Error() {
        val taskToAdd = TaskEntity(37, "New Task", "New Content", 100, 200, 0)
        coEvery { dao.addTask(taskToAdd) } throws Throwable()
        runTest {
            assertFailsWith<Throwable> {
                taskRepositoryImpl.addTask(taskToAdd.toModel())
            }
        }
    }

    @Test
    fun when_DeleteTask_Expected_Success() {
        val taskId = 37L
        coEvery { dao.removeTask(taskId) } just Runs
        runTest {
            taskRepositoryImpl.removeTask(taskId)
            coVerify { dao.removeTask(taskId) }
        }
    }

    @Test
    fun when_DeleteTask_Expected_Error() {
        val taskId = 37L
        coEvery { dao.removeTask(taskId) } throws Throwable()
        runTest {
            assertFailsWith<Throwable> {
                taskRepositoryImpl.removeTask(taskId)
            }
        }
    }
}
