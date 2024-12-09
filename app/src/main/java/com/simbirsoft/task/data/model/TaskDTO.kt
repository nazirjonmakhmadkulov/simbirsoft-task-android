package com.simbirsoft.task.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskDTO(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val title: String,
    @SerialName("description")
    val content: String,
    @SerialName("date_start")
    val startDate: Long,
    @SerialName("date_finish")
    val endDate: Long
)