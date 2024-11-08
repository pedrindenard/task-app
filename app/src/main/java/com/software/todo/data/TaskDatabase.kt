package com.software.todo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.software.todo.domain.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun dao(): TaskDao

    companion object {
        fun getInstance(context: Context): TaskDao {
            return Room.databaseBuilder(context, TaskDatabase::class.java, "tasks")
                .fallbackToDestructiveMigration()
                .build()
                .dao()
        }
    }

}