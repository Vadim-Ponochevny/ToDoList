package com.vpnch.todolist.data.model

import com.vpnch.todolist.domain.model.Todo

// API → Room → Domain
fun TodoDto.toEntity() = TodoEntity(
    id = id ?: 0,
    userId = userId,
    title = title,
    completed = completed)

fun TodoEntity.toDomain() = Todo(
    id = id,
    userId = userId,
    title = title,
    completed = completed
)

// Domain → Room
fun Todo.toEntity() = TodoEntity(
    id = id,
    userId = userId,
    title = title,
    completed = completed
)
