package com.software.todo.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.software.todo.data.TaskDao
import com.software.todo.domain.Task
import com.software.todo.domain.TaskSheet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel(
    private val dao: TaskDao
) : ViewModel() {

    private val _state: MutableStateFlow<TaskSheet> = MutableStateFlow(TaskSheet.Closed)
    val state: StateFlow<TaskSheet> = _state.asStateFlow()

    /* ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- */

    val tasks: StateFlow<List<Task>> = dao
        .getAllTasks()
        .asStateFlow()

    /* ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- */

    private fun <T> Flow<List<T>>.asStateFlow(): StateFlow<List<T>> {
        return stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    /* ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- */

    fun insert(task: Task) = viewModelScope.launch {
        withContext(Dispatchers.IO) { dao.insert(task) }
    }

    fun update(task: Task) = viewModelScope.launch {
        withContext(Dispatchers.IO) { dao.update(task) }
    }

    fun delete(task: Task) = viewModelScope.launch {
        withContext(Dispatchers.IO) { dao.delete(task) }
    }

    /* ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- */

    fun update(update: (TaskSheet) -> TaskSheet) {
        _state.update(update)
    }

}