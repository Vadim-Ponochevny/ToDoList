package com.vpnch.todolist.data.model

data class TodoDto(
    val userId: Int,
    val id: Int? = null,
    val title: String,
    val completed: Boolean
)