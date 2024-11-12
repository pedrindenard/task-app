@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@file:Suppress("SpellCheckingInspection")

package com.software.todo.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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

    // Instancia o ViewModel utilizando a função viewModels, que permite
    // a criação de ViewModel com um ViewModelProvider
    // personalizado.
    //
    // ViewModelFactory fornece uma maneira de inicializar o
    // TaskViewModel com a instância do
    // TaskDatabase.
    private val viewModel by viewModels<TaskViewModel> {
        viewModelFactory { // Bloco para criar a inicialização do ViewModel
            initializer { // Bloco para inicializar o ViewModel
                TaskViewModel( // Instância do ViewModel (igual uma class)
                    TaskDatabase.getInstance(applicationContext) // Instância do banco de dados
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Theme {
                TaskScreen()
            }
        }
    }

    /* ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- */

    @Composable
    private fun TaskScreen() {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior() // Observa sempre que o
                                                                      // usuário rola a lista de tarefas

        Scaffold( // Scaffold tem a estrutura base para criarmos o nosso layout
            modifier = Modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection), // Add o observer
            floatingActionButton = { TaskActionButton() }, // Lambda para incluir o botão flutuante
            topBar = { TaskTopBar(scrollBehavior) } // Lambda para incluir a barra de ações
        ) { paddingValues ->
            val items by viewModel.tasks.collectAsState() // Busca as tarefas cadastradas no bd
            val state by viewModel.state.collectAsState() // Verifica o status atual do bottom sheet

            if (
                items.isNotEmpty() // Se a lista não estiver vazia, queremos mostra as tarefas
            ) {
                TaskListScreen(items, paddingValues) // Lista de tarefas
            } else { // Caso contrário
                TaskEmptyScreen(paddingValues) // Mensagem informativa de que não há nenhuma tarefa
            }

            // When em kotlin é igual o IF, IF ELSE
            // OBS: Funciona apenas com classes seladas e enums
            when (state) { // Quando o 'state' for:
                is TaskSheet.Insert -> { // Insert --> Execute esse trecho de código
                    TaskModalBottomSheet(text = "Inserir",
                        onDismissRequest = {
                            viewModel.update { TaskSheet.Closed }
                        },
                        onConfirm = { task ->
                            viewModel.insert(task)
                        }
                    )
                }

                is TaskSheet.Update -> { // Update --> Execute esse trecho de código
                    val update = state as TaskSheet.Update

                    TaskModalBottomSheet(text = "Editar",
                        onDismissRequest = {
                            viewModel.update { TaskSheet.Closed }
                        },
                        onConfirm = { task ->
                            viewModel.update(task)
                        },
                        task = update.task
                    )
                }

                is TaskSheet.Closed -> { // Closed --> Execute esse trecho de código
                    // Não faça nada no status de fechado
                }
            }
        }
    }

    @Composable
    private fun TaskActionButton() {
        FloatingActionButton( // Botão flutuante com bordas arredondadas
            onClick = { // Lambda contendo a ação de click do usuário
                viewModel.update { TaskSheet.Insert }
            }
        ) {
            Icon( // Layout para desenhar uma imagem
                imageVector = Icons.Default.AddTask, // Imagem a qual queremos desenhar
                contentDescription = "Nova tarefa" // Recurso de acessibilidade
            )
        }
    }

    @Composable
    private fun TaskTopBar(scrollBehavior: TopAppBarScrollBehavior) {
        TopAppBar( // Barra de ações com título
            scrollBehavior = scrollBehavior, title = { // Lambda para desenhar o layout 'Text'
                Text(
                    text = "Tarefas" // Texto que queremos escrever
                )
            }
        )
    }

    /* ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- */

    @Composable // Indica para o compilador que essa função é um layout
    private fun TaskListScreen(items: List<Task>, padding: PaddingValues) {
        LazyColumn(contentPadding = padding) { // Visualização de items (layout) na vertical
            items( // Scopo para obter os items e transformar em Composable (layout)
                items = items,
                key = { task -> task.id }
            ) { task -> // Lambda contendo o objeto tarefa
                TaskItem(task = task) // Nossa tarefa. Para cada item na lista, essa função é chamada
            }
        }
    }

    @Composable
    private fun TaskEmptyScreen(padding: PaddingValues) {
        Column( //
            modifier = Modifier
                .padding(paddingValues = padding) // Espaçamentos do Scaffold (TopBar)
                .fillMaxSize(), // Signica que esse componente vai preencher a tela inteira (Altura e Largura)
            horizontalAlignment = Alignment.CenterHorizontally, // Alinhados horizontalmente no centro
            verticalArrangement = Arrangement.Center // Alinhados verticamente no centro
        ) {
            Text(
                modifier = Modifier.padding(vertical = 16.dp), // Espaçamentos
                fontWeight = FontWeight.SemiBold, // Formato de fonte em semi negrito
                text = "Não há nada por aqui!" // Texto que queremos escrever
            )

            Icon( // Layout para desenhar uma imagem
                imageVector = Icons.Default.DoneAll, // Imagem a qual queremos desenhar
                contentDescription = "Sem tarefas!" // Recurso de acessibilidade
            )
        }
    }

    @Composable
    private fun TaskItem(task: Task) {
        Card( // Layout com bordas e sombra (efeito flutuante)
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp) // Espaçamentos
                .combinedClickable( // Clique longo (pressiona e segura) e clique (clica e solta)
                    onLongClick = { viewModel.delete(task) }, // Deleta a tarefa
                    onClick = {
                        viewModel.update { TaskSheet.Update(task) }
                    }
                )
        ) {
            Row { // Tudo o que estiver dentro do lambda vai ser adicionado na horizontal (linha)
                Column( // Tudo o que estiver dentro do lambda vai ser adicionado na vertical (coluna)
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp) // Espaçamentos
                        .weight(weight = 1F) // Significa que deve preencher a
                                             // tela na horizontal levando em consideração os outros layouts
                ) {
                    Text( // Layout para desenhar um texto
                        modifier = Modifier.fillMaxWidth(), // Indica ao compose que esse componte vai
                                                            // preencher toda a tela na horizontal

                        fontWeight = FontWeight.Bold, // Formato de fonte em negrito
                        text = task.title // Texto que queremos escrever (título da tarefa)
                    )

                    Text( // Layout para desenhar um texto
                        modifier = Modifier.fillMaxWidth(), // Indica ao compose que esse componte vai
                                                            // preencher toda a tela na horizontal
                        fontWeight = FontWeight.Light, // Formato de fonte mais fina
                        text = task.description // Texto que queremos escrever (descrição da tarefa)
                    )
                }

                Checkbox( // Caixa com a possibilidade de marcar ou desmarcar
                    checked = task.checked, // Recebe um valor do tipo [Boolean] (true ou false)
                    onCheckedChange = { newValue -> // Lambda para capturar a ação de quando ocorre o click na caixa
                        viewModel.update(
                            task.copy(checked = newValue)
                        )
                    }
                )
            }
        }
    }
}