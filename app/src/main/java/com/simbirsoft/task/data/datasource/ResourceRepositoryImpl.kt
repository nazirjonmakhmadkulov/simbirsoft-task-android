package com.simbirsoft.task.data.datasource

import android.content.Context
import androidx.core.graphics.toColorInt
import com.simbirsoft.task.common.Colors
import com.simbirsoft.task.common.readTasksFromJson
import com.simbirsoft.task.data.mapping.toEntity
import com.simbirsoft.task.data.mapping.toModel
import com.simbirsoft.task.domain.model.Task
import com.simbirsoft.task.domain.repository.ResourceRepository
import javax.inject.Inject
import kotlin.random.Random

class ResourceRepositoryImpl @Inject constructor() : ResourceRepository {
    override suspend fun readJson(context: Context): List<Task> {
        val colors = Colors.entries
        val color = colors[Random.nextInt(0, colors.size - 1)].hex.toColorInt()
        return context.readTasksFromJson().map { it.toEntity(color).toModel() }
    }
}