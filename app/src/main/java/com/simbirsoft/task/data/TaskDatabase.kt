package com.simbirsoft.task.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.simbirsoft.task.data.TaskDatabase.Companion.DATABASE_VERSION
import com.simbirsoft.task.data.dao.TaskDao
import com.simbirsoft.task.data.model.TaskEntity

@Database(entities = [TaskEntity::class], version = DATABASE_VERSION, exportSchema = true)
abstract class TaskDatabase : RoomDatabase() {
    companion object {
        private const val DATABASE_VERSION = 1
    }

    abstract fun taskDao(): TaskDao
}