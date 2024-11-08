package com.software.todo.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(

    @ColumnInfo("title")
    val title: String = "",

    @ColumnInfo("description")
    val description: String = "",

    @ColumnInfo("checked")
    val checked: Boolean = false,

    @PrimaryKey(true)
    val id: Int = 0

)