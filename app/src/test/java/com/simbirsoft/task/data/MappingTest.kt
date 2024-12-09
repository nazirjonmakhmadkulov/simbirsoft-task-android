package com.simbirsoft.task.data

import com.simbirsoft.task.data.mapping.toModel
import com.simbirsoft.task.data.model.TaskEntity
import kotlin.test.Test
import kotlin.test.assertEquals

class MappingTest {

    @Test
    fun should_map_TaskEntity_to_Task_correctly() {
        val entity = TaskEntity(37, "My task", "content", 121, 122, 0)
        val domainModel = entity.toModel()
        assertEquals(37, domainModel.id)
        assertEquals("My task", domainModel.title)
    }

    @Test
    fun should_handle_empty_fields() {
        val entity = TaskEntity(0, "", "", 0, 0, 0)
        val domainModel = entity.toModel()
        assertEquals(0, domainModel.id)
        assertEquals("", domainModel.title)
        assertEquals("", domainModel.content)
        assertEquals(0, domainModel.color)
    }
}