package com.vpnch.todolist.di

import com.vpnch.todolist.data.database.TodoDao
import com.vpnch.todolist.data.remote.TodoApi
import com.vpnch.todolist.data.repository.TodoRepositoryImpl
import com.vpnch.todolist.domain.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTodoRepository(
        dao: TodoDao,
        api: TodoApi
    ): TodoRepository = TodoRepositoryImpl(dao, api)
}