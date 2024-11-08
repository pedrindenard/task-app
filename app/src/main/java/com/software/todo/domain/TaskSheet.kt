package com.software.todo.domain

import androidx.compose.runtime.Immutable

@Immutable
sealed class TaskSheet {

    data object Insert : TaskSheet()

    data class Update(val task: Task) : TaskSheet()

    data object Closed : TaskSheet()

}