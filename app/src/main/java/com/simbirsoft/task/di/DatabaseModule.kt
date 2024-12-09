package com.simbirsoft.task.di

import android.content.Context
import androidx.room.Room
import com.simbirsoft.task.data.TaskDatabase
import com.simbirsoft.task.data.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    companion object {
        const val DATABASE_NAME = "simbirsoft_task_db"
    }

    @Provides
    fun database(@ApplicationContext context: Context): TaskDatabase =
        Room.databaseBuilder(context, TaskDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideTaskDao(db: TaskDatabase): TaskDao = db.taskDao()
}