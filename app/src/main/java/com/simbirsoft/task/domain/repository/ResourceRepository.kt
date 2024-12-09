package com.simbirsoft.task.domain.repository

import android.content.Context
import com.simbirsoft.task.domain.model.Task

interface ResourceRepository {
    suspend fun readJson(context: Context) : List<Task>
}