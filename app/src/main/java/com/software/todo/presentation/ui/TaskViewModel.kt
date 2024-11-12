@file:Suppress("SpellCheckingInspection")

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

// Ao herdar a classe ViewModel, estamos criando uma classe especializada para manter e gerenciar
// os dados de UI de maneira isolada das Activities
class TaskViewModel(
    private val dao: TaskDao // Injeta uma instância do DAO para acessar as operações de bd
) : ViewModel() {

    // Declara uma propriedade privada `_state` como MutableStateFlow
    // que armazena o estado atual de TaskSheet
    //
    // Inicialmente, `_state` é definido como `TaskSheet.Closed`,
    // indicando que o painel de tarefas começa fechado
    private val _state: MutableStateFlow<TaskSheet> = MutableStateFlow(TaskSheet.Closed)

    // Exponibiliza uma versão imutável de `_state` chamada `state`
    // para outras partes do código
    //
    // Isso é feito usando `.asStateFlow()`, que transforma `_state` em um StateFlow
    // para que apenas essa classe possa modificar o estado
    val state: StateFlow<TaskSheet> = _state.asStateFlow()

    /* ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- */

    // Variavel publica que obtem as tarefas cadastradas banco de dados
    val tasks: StateFlow<List<Task>> = dao
        .getAllTasks()
        .asStateFlow() // Converte o `Flow` em um `StateFlow`

    /* ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- */

    // Extensão para converter um `Flow` em `StateFlow` com um
    // escopo de `viewModelScope`. `stateIn` inicia o fluxo de forma preguiçosa
    // e usa uma lista vazia como valor inicial
    private fun <T> Flow<List<T>>.asStateFlow(): StateFlow<List<T>> {
        return stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    /* ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- */

    // Executa a inserção da tarefa em um escopo de `viewModelScope` para que a coroutine
    // seja cancelada ao final do ciclo de vida do `ViewModel`
    fun insert(task: Task) = viewModelScope.launch {
        withContext(Dispatchers.IO) { // Executa a atualização da tarefa de forma assíncrona
            dao.insert(task) // Insere uma nova tarefa no banco de dados
        }
    }

    // Executa a atualização da tarefa em um escopo de `viewModelScope` para que a coroutine
    // seja cancelada ao final do ciclo de vida do `ViewModel`
    fun update(task: Task) = viewModelScope.launch {
        withContext(Dispatchers.IO) { // Executa a atualização da tarefa de forma assíncrona
            dao.update(task) // Atualiza a tarefa no banco de dados
        }
    }

    // Executa a exclusão da tarefa em um escopo de `viewModelScope` para que a coroutine
    // seja cancelada ao final do ciclo de vida do `ViewModel`
    fun delete(task: Task) = viewModelScope.launch {
        withContext(Dispatchers.IO) { // Executa a atualização da tarefa de forma assíncrona
            dao.delete(task) // Deleta a tarefa do banco de dados
        }
    }

    /* ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- */

    // Função responsável por atualizar o status do nosso ModalBottomSheet
    fun update(update: (TaskSheet) -> TaskSheet) {
        _state.update(update)
    }

}