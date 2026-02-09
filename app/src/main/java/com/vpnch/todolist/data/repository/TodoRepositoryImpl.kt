package com.vpnch.todolist.data.repository

import android.util.Log
import com.vpnch.todolist.data.database.TodoDao
import com.vpnch.todolist.data.model.TodoDto
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
        try {
            val serverTodo = api.createTodo(
                TodoDto(userId = 1, title = title, completed = false)
            )
            dao.insert(serverTodo.toEntity())
        } catch (e: Exception) {
            Log.i("Offline mode", "API failed: ${e.message}")
            val maxId = dao.getMaxId() ?: 0
            val localTodo = Todo(
                id = maxId + 1,
                userId = 1,
                title = title,
                completed = false
            )
            dao.insert(localTodo.toEntity())
        }
    }


    override suspend fun deleteTodo(todo: Todo) {
        try {
            api.deleteTodo(todo.id)
            dao.delete(todo.toEntity())
        } catch (e: Exception) {
            Log.i("Offline mode", "DELETE API failed: ${e.message}")
            dao.delete(todo.toEntity())
        }
    }

    override suspend fun toggleTodo(todo: Todo) {
        try {
            val updatedTodo = todo.copy(completed = !todo.completed)
            val serverTodo = api.updateTodo(
                id = todo.id,
                todo = TodoDto(
                    id = todo.id,
                    userId = todo.userId,
                    title = todo.title,
                    completed = updatedTodo.completed
                )
            )
            dao.insert(serverTodo.toEntity())
        } catch (e: Exception) {
            Log.i("Offline mode", "TOGGLE API failed: ${e.message}")
            dao.insert(todo.copy(completed = !todo.completed).toEntity())
        }
    }

}