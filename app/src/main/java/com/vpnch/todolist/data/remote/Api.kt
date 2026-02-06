package com.vpnch.todolist.data.remote

import com.vpnch.todolist.data.model.TodoDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
interface TodoApi {
    @GET("todos")
    suspend fun getTodos(): List<TodoDto>

    @POST("todos")
    suspend fun createTodo(@Body todo: TodoDto): TodoDto
}

object NetworkModule {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val api: TodoApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoApi::class.java)
    }
}