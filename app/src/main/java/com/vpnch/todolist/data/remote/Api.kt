package com.vpnch.todolist.data.remote

import com.vpnch.todolist.data.model.TodoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TodoApi {
    @GET("todos")
    suspend fun getTodos(): List<TodoDto>

    @POST("todos")
    suspend fun createTodo(@Body todo: TodoDto): TodoDto
}

