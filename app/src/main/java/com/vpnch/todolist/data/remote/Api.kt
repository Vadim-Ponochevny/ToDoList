package com.vpnch.todolist.data.remote

import com.vpnch.todolist.data.model.TodoDto
import retrofit2.http.*

interface TodoApi {
    @GET("todos")
    suspend fun getTodos(): List<TodoDto>

    @POST("todos")
    suspend fun createTodo(@Body todo: TodoDto): TodoDto

    @DELETE("todos/{id}")
    suspend fun deleteTodo(@Path("id") id: Int)

    @PUT("todos/{id}")
    suspend fun updateTodo(
        @Path("id") id: Int,
        @Body todo: TodoDto
    ): TodoDto
}

