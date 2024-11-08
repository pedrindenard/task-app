@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.software.todo.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.software.todo.data.TaskDatabase
import com.software.todo.domain.Task
import com.software.todo.domain.TaskSheet
import com.software.todo.presentation.sheet.TaskModalBottomSheet
import com.software.todo.presentation.theme.Theme

class TaskActivity : ComponentActivity() {

    private val viewModel by viewModels<TaskViewModel> {
        viewModelFactory {
            initializer {
                TaskViewModel(
                    TaskDatabase.getInstance(applicationContext)
                )
            }
        }
    }

    /* ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Theme {
                Screen()
            }
        }
    }

    /* ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- */

    @Composable
    private fun Screen() {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            modifier = Modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection),
            floatingActionButton = { TaskActionButton() },
            topBar = { TaskTopBar(scrollBehavior) }
        ) { contentPadding ->
            val items by viewModel.tasks.collectAsState()
            val state by viewModel.state.collectAsState()

            AnimatedVisibility(
                visible = items.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                LazyColumn(contentPadding = contentPadding) {
                    items(
                        items = items, key = { task -> task.id }
                    ) { task ->
                        TaskItem(task = task)
                    }
                }
            }

            AnimatedVisibility(
                visible = items.isEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                TasksEmpty(
                    contentPadding = contentPadding
                )
            }

            AnimatedContent(label = "Sheet", targetState = state) { sheet ->
                when (sheet) {
                    is TaskSheet.Insert -> TaskModalBottomSheet(
                        onDismissRequest = {
                            viewModel.update { TaskSheet.Closed }
                        },
                        onConfirm = { task ->
                            viewModel.insert(task)
                        },
                        label = "Create"
                    )

                    is TaskSheet.Update -> TaskModalBottomSheet(
                        task = sheet.task,
                        onDismissRequest = {
                            viewModel.update { TaskSheet.Closed }
                        },
                        onConfirm = { task ->
                            viewModel.update(task)
                        },
                        label = "Update"
                    )

                    is TaskSheet.Closed -> Unit
                }
            }
        }
    }

    /* ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- */

    @Composable
    private fun TaskActionButton() {
        FloatingActionButton(onClick = {
            viewModel.update { TaskSheet.Insert }
        }) {
            Icon(
                imageVector = Icons.Default.AddTask, contentDescription = "Create new task"
            )
        }
    }

    @Composable
    private fun TaskTopBar(scrollBehavior: TopAppBarScrollBehavior) {
        TopAppBar(
            scrollBehavior = scrollBehavior, title = {
                Text(text = "Tasks")
            }
        )
    }

    /* ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- */

    @Composable
    private fun TaskItem(task: Task) {
        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .combinedClickable(
                    onLongClick = { viewModel.delete(task) },
                    onClick = {
                        viewModel.update { TaskSheet.Update(task) }
                    }
                )
        ) {
            Row {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .weight(weight = 1F)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        text = task.title
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Light,
                        text = task.description
                    )
                }

                Checkbox(
                    checked = task.checked,
                    onCheckedChange = { newValue ->
                        viewModel.update(
                            task.copy(checked = newValue)
                        )
                    }
                )
            }
        }
    }

    /* ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- */

    @Composable
    private fun TasksEmpty(contentPadding: PaddingValues) {
        Column(
            modifier = Modifier
                .padding(paddingValues = contentPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(vertical = 16.dp),
                fontWeight = FontWeight.SemiBold,
                text = "Ops, nothing here!"
            )

            Icon(
                imageVector = Icons.Default.DoneAll, contentDescription = "Out of tasks!"
            )
        }
    }

}