package com.simbirsoft.task.data.mapping

import android.R
import com.simbirsoft.task.common.toLocalDate
import com.simbirsoft.task.common.toMillis
import com.simbirsoft.task.data.model.TaskDTO
import com.simbirsoft.task.data.model.TaskEntity
import com.simbirsoft.task.domain.model.Task
import com.simbirsoft.task.presentation.customView.TaskViewTaskModel

fun Task.toEntity() = TaskEntity(
    id = this.id,
    title = this.title,
    content = this.content,
    startDate = this.startDate.toMillis(),
    endDate = this.endDate.toMillis(),
    color = this.color,
)

fun TaskDTO.toEntity(color: Int) = TaskEntity(
    id = this.id,
    title = this.title,
    content = this.content,
    startDate = this.startDate,
    endDate = this.endDate,
    color = color
)

fun TaskEntity.toModel() = Task(
    id = this.id,
    title = this.title,
    content = this.content,
    startDate = this.startDate.toLocalDate(),
    endDate = this.endDate.toLocalDate(),
    color = this.color,
)

fun Task.toViewTaskModel() = TaskViewTaskModel(
    id = this.id,
    color = this.color,
    taskName = this.title,
    timeStart = this.startDate,
    timeEnd = this.endDate,
)