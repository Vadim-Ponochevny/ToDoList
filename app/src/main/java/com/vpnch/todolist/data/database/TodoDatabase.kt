package com.vpnch.todolist.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vpnch.todolist.data.model.TodoEntity

@Database(
    entities = [TodoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        const val DATABASE_NAME = "todos_table"
    }
}