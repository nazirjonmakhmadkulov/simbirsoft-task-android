package com.simbirsoft.task.di

import com.simbirsoft.task.data.datasource.ResourceRepositoryImpl
import com.simbirsoft.task.data.datasource.TaskRepositoryImpl
import com.simbirsoft.task.domain.repository.ResourceRepository
import com.simbirsoft.task.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository

    @Binds
    fun bindResourceRepository(impl: ResourceRepositoryImpl): ResourceRepository
}