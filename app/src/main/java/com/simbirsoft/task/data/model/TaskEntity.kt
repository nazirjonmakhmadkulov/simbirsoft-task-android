package com.simbirsoft.task.data.model

import androidx.core.graphics.toColorInt
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.simbirsoft.task.common.Colors
import kotlin.random.Random

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val content: String,
    val startDate: Long,
    val endDate: Long,
    val color: Int = Colors.entries[Random.nextInt(0, 17)].hex.toColorInt(),
)
