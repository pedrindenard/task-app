@file:OptIn(ExperimentalMaterial3Api::class)

package com.software.todo.presentation.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.software.todo.domain.Task

@Composable
fun TaskModalBottomSheet(
    label: String, task: Task = Task(), onDismissRequest: () -> Unit, onConfirm: (Task) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest
    ) {
        var title by remember { mutableStateOf(value = task.title) }
        ModalOutlinedTextField(
            text = "Title",
            onValueChanged = { newValue -> title = newValue },
            value = title
        )

        var description by remember { mutableStateOf(value = task.description) }
        ModalOutlinedTextField(
            text = "Description",
            onValueChanged = { newValue -> description = newValue },
            value = description
        )

        var checked by remember { mutableStateOf(value = task.checked) }
        ModalCheckBox(
            onValueChanged = { newValue -> checked = newValue },
            value = checked
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            ModalButton(label = label) {
                onDismissRequest()
                onConfirm(
                    task.copy(title = title, description = description, checked = checked)
                )
            }
        }

        Spacer(
            modifier = Modifier.navigationBarsPadding()
        )
    }
}

@Composable
private fun ModalOutlinedTextField(text: String, value: String, onValueChanged: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth(),
        label = {
            Text(text = text)
        },
        onValueChange = onValueChanged,
        value = value
    )
}

@Composable
private fun ModalCheckBox(value: Boolean, onValueChanged: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier.padding(horizontal = 8.dp),
            onCheckedChange = onValueChanged,
            checked = value
        )

        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            fontWeight = FontWeight.Light,
            text = "Done"
        )
    }
}

@Composable
private fun ModalButton(label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Button(
            onClick = onClick
        ) {
            Text(label)
        }
    }
}