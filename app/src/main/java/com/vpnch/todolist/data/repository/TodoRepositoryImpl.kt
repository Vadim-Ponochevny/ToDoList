package com.vpnch.todolist.data.repository

import com.vpnch.todolist.data.database.TodoDao
import com.vpnch.todolist.data.model.toDomain
import com.vpnch.todolist.data.model.toEntity
import com.vpnch.todolist.data.remote.TodoApi
import com.vpnch.todolist.domain.model.Todo
import com.vpnch.todolist.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val dao: TodoDao,
    private val api: TodoApi
) : TodoRepository {

    override val todosFlow: Flow<List<Todo>> = dao.getTodosFlow()
        .map { entities -> entities.map { it.toDomain() } }

    override suspend fun syncWithRemote() {
        val remoteTodos = api.getTodos()
        dao.insertAll(remoteTodos.map { it.toEntity() })
    }

    override suspend fun addTodo(title: String) {
        val maxId = dao.getMaxId() ?: 0
        val localTodo = Todo(maxId + 1, 1, title, false)
        dao.insert(localTodo.toEntity())
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.delete(todo.toEntity())
    }

    override suspend fun toggleTodo(todo: Todo) {
        dao.insert(todo.copy(completed = !todo.completed).toEntity())
    }
}