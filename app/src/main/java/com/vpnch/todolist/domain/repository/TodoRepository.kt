package com.vpnch.todolist.domain.repository

import com.vpnch.todolist.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    val todosFlow: Flow<List<Todo>>

    suspend fun syncWithRemote()

    suspend fun addTodo(title: String)
    suspend fun deleteTodo(todo: Todo)
    suspend fun toggleTodo(todo: Todo)
}