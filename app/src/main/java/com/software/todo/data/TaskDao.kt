@file:Suppress("SpellCheckingInspection")

package com.software.todo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.software.todo.domain.Task
import kotlinx.coroutines.flow.Flow

@Dao // @Dao define esta interface como um DAO (Data Access Object) para a entidade `Task`
interface TaskDao {

    // Consulta para buscar todas as tarefas da tabela `task`
    @Query("SELECT * FROM task")
    fun getAllTasks(): Flow<List<Task>> // Retorna uma lista de tarefas em um fluxo reativo

    // Anotação para inserir uma nova tarefa na tabela `task`
    @Insert
    fun insert(task: Task)

    // Anotação para atualizar uma tarefa existente na tabela `task`
    @Update
    fun update(task: Task)

    // Anotação para deletar uma tarefa existente da tabela `task`
    @Delete
    fun delete(task: Task)

}